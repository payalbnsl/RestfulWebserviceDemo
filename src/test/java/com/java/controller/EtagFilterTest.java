package com.java.controller;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.Address;
import com.java.dto.Person;
import com.java.dto.Person1;
import com.java.dto.Person2PersonMapper;
//spring-test
public class EtagFilterTest {

	/*Testing get method for etag*/
	/*@Test
	public void test1() {
		RestTemplate template= new RestTemplate();
		HttpHeaders header= new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity= new HttpEntity<>(header);
		ResponseEntity<String> response= template.exchange("http://localhost:8080/v1/persons/{personid}", HttpMethod.GET, entity, String.class, 1);
		System.out.println(response);
		List<String> etag= response.getHeaders().get("Etag");
		assertNotNull(etag);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		header.setIfNoneMatch(etag);
		entity= new HttpEntity<>(header);
		response= template.exchange("http://localhost:8080/v1/persons/{personid}", HttpMethod.GET, entity, String.class, 1);
		assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
		assertNull(response.getBody());
	}*/
	
	/*Testing put method using etag*/
	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		HttpHeaders header= new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		ObjectMapper mapper= new ObjectMapper();
		RestTemplate template = new RestTemplate();
		Person p1 = new Person();
		p1.setName("payal");
		p1.setEmailId("payal@rjt");
		p1.setAddress(new Address(10, "meerut", "UP", 250002));
		HttpEntity<String> entity= new HttpEntity<>(mapper.writeValueAsString(Person2PersonMapper.getObject((p1))),header);
		ResponseEntity<String> response = template.postForEntity("http://localhost:8080/v1/persons", entity, String.class);
		Person1 p=mapper.readValue(response.getBody(), Person1.class);
		int id=p.getPersonId();
		
		HttpHeaders header2= new HttpHeaders();
		header2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 entity= new HttpEntity<>(header2);
		response=template.exchange( "http://localhost:8080/v1/persons/{personid}",HttpMethod.GET,entity, String.class, id);
		List<String> etag=response.getHeaders().get("Etag");
		System.out.println(etag);
		HttpHeaders header1= new HttpHeaders();
		header1.setContentType(MediaType.APPLICATION_JSON);
		
		p.setName(p.getName()+ "Modified!");
		String newData=mapper.writeValueAsString(p);
		entity= new HttpEntity<>(newData,header1);
		ResponseEntity<String > response1= template.exchange("http://localhost:8080/v1/persons/{personid}", HttpMethod.PUT, entity, String.class, id);
		assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
		
		response=template.exchange( "http://localhost:8080/v1/persons/{personid}",HttpMethod.GET,entity, String.class, id);
		List<String> etag1=response.getHeaders().get("Etag");
		System.out.println(etag1);
		
		p.setName(p.getName()+ "Agaian Modified!");
		newData=mapper.writeValueAsString(p);
		header1.setIfMatch(etag);
		 entity= new HttpEntity<>(newData,header1);
		 response1= template.exchange("http://localhost:8080/v1/persons/{personid}", HttpMethod.PUT, entity, String.class, id);
		assertEquals(HttpStatus.PRECONDITION_FAILED, response1.getStatusCode());
		System.out.println(response1.getBody());
		
		//get : etag | update | update: etag: 412
	}
}

