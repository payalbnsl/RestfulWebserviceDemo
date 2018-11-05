package com.java.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.java.dto.Person;
import com.java.dto.Person1;
import com.java.dto.Person2PersonMapper;
import com.java.service.PersonService;

@RestController
@RequestMapping(path = "/v1/persons")
public class PersonController1 {

	@Autowired
	PersonService service;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person1> fetchById(@PathVariable("id") int id) {
		Optional<Person> person = service.getOne(id);
		Person p = person.orElse(new Person()); // <person/>
		Link link= ControllerLinkBuilder.linkTo(AddressController.class, id).withRel("address-info");
		Person1 obj= Person2PersonMapper.getObject(p);
		obj.removeLinks();
		obj.add(link);
		return ResponseEntity.ok().eTag(String.valueOf(p.getVersion())).body(obj);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ArrayList<Person1> fetchAll() {
		List<Person> persons = service.findAll();
		ArrayList<Person1> list = new ArrayList<>();
		for (Person p : persons) {
			list.add(Person2PersonMapper.getObject(p));
		}
		return list;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updatePerson(@PathVariable("id") int id, @RequestBody String data, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		Person p=service.getOne(id).get();
		if(p==null) {
			return ResponseEntity.notFound().build();
		}
		//conditional put: optimistic locking| 2 concurrent request| over-writting the data
		String ifMatchValue=request.getHeader("If-Match");
		if(ifMatchValue == null) {
			return ResponseEntity.badRequest().build();
		}
		if(String.valueOf(p.getVersion())!= ifMatchValue) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		Person1 obj = mapper.readValue(data, Person1.class);
		Person person = Person2PersonMapper.getObject(obj);
		person.setPersonId(id);
		Person p1=service.updatePerson(person);
		return ResponseEntity.ok().eTag(""+p1.getVersion()).build();
	}

	/*
	 * @TODO Patch: partial updates
	 */
	@PatchMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void updatePersonPartially(@PathVariable("id") int id, @RequestBody String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Person1 p = Person2PersonMapper.getObject(service.getOne(id).get());
		ObjectNode node = mapper.readValue(data, ObjectNode.class);
		if (node.has("name")) {
			p.setName(node.get("name").asText());
		}
		if (node.has("phoneNumber")) {
			p.setPhoneNumber(node.findValues("phoneNumber").stream().map(x -> x.asLong()).collect(Collectors.toList()));
		}
		if (node.has("emailId")) {
			p.setEmailId(node.get("emailId").asText());
		}
		Person person = Person2PersonMapper.getObject(p);
		person.setPersonId(id);
		service.updatePerson(person);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Person deleted successfully!")
	public void deletePerson(@PathVariable("id") int id) {
		service.deletePerson(id);
	}

	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person1> addPerson(@RequestBody String data, HttpServletRequest request,
			HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		HttpHeaders header = new HttpHeaders();
		List<String> values = Arrays.asList("application/json", "application/xml");
		header.addAll("content-type", values);
		ObjectMapper mapper = new ObjectMapper();
		Person1 obj = mapper.readValue(data, Person1.class);
		Person person = Person2PersonMapper.getObject(obj);
		Person p = service.savePerson(person);
		/*String url = request.getRequestURI().toString();
		URI uri = new UriTemplate("{url}/{idOfNewResource}").expand(url, p.getId()); // http://localhost:8080/persons/4
		response.setHeader("Location", uri.toASCIIString());*/
		Person1 obj1= Person2PersonMapper.getObject(p);
		Link link= ControllerLinkBuilder.linkTo(PersonController1.class).slash(obj1.getPersonId()).withSelfRel();
		System.out.println(link);
		obj1.removeLinks();
		obj1.add(link);
		return new ResponseEntity(obj1, header, HttpStatus.CREATED);
	}
}
