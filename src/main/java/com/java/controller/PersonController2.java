package com.java.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import com.java.dto.Person;
import com.java.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Api(value="PersonController: version 2", description="Revised version for person service. Changed phonenumber to be of type map. Saved as key-value pair with type as key ")
@RestController
@RequestMapping(path = "/v2/persons")
public class PersonController2 {

	@Autowired
	PersonService service;
	@ApiOperation(value="Fetch a particular person by it's id",response=Person.class,produces="application/json, application/xml")
	//@ApiParam(value="Person id", allowableValues="range[1, infinity]", allowEmptyValue=false)
	@ApiResponse(code=200,reference="Person object", message="Object retrieved successfully")
	@GetMapping(path = "/{id}",produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person> fetchById(@PathVariable("id") int id) {
		Optional<Person> person = service.getOne(id);
		Person obj=person.orElse(new Person()); // <person/>
		return ResponseEntity.ok(obj);
	}

	@GetMapping(params="name",produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Person> fetchByName(@ApiParam(value="Name of the person")@RequestParam("name") String name) {
		Optional<Person> person = service.getByName(name);
		Person obj=person.orElse(new Person()); // <person/>
		return ResponseEntity.ok(obj);
	}
	
	
	@GetMapping(params= {"limit", "offset"},produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Person>> fetchByPagination(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
		Page<Person> person = service.getByName(offset, limit);
		return ResponseEntity.ok(person.getContent());
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE })
	public List<Person> fetchAll() {
		List<Person> persons = service.findAll();
		return persons;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Person updated successfully!")
	public void updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
		person.setPersonId(id);
		service.updatePerson(person);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Person deleted successfully!")
	public void deletePerson(@PathVariable("id") int id) {
		service.deletePerson(id);
	}

	//person: 6
	@PostMapping( consumes = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void addPerson(@RequestBody Person person, HttpServletRequest request, HttpServletResponse response) {
		HttpHeaders header = new HttpHeaders();
		List<String> values= Arrays.asList("application/json","application/xml");
		header.addAll("content-type", values);
		Person p=service.savePerson(person);
		String url= request.getRequestURI().toString();
		URI uri= new UriTemplate("{url}/{idOfNewResource}").expand(url, p.getId()); //http://localhost:8080/persons/4  
		response.setHeader("Location", uri.toASCIIString());
	}
	
	@GetMapping(params= {"id", "states"},produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Person>> getPersonsBySpec(@RequestParam int id, @RequestParam String... states){
		return	ResponseEntity.ok(service.getBySpec(id, states));
	}
}
