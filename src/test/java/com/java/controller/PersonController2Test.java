package com.java.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dto.Address;
import com.java.dto.Person;
import com.java.dto.Person1;

public class PersonController2Test {
@Ignore
	@Test
	public void test1() {
		RestTemplate restTemplate = new RestTemplate();
		Person p = new Person();
		p.setName("payal");
		p.setEmailId("payal@rjt");
		p.setAddress(new Address(10, "meerut", "UP", 250002));
		URI location = restTemplate.postForLocation("http://localhost:8080/v2/persons", p);
		assertNotNull(location);
		assertThat(location.toString(), startsWith("/v2/persons/"));
	}


	@Test
	public void test3() throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		Person1 p = new Person1();
		p.setName("payal");
		p.setEmailId("payal@rjt");
		p.setAddress(new Address(10, "meerut", "UP", 250002));
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(p);
		System.out.println(str);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(str, header);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/persons", HttpMethod.POST,
				entity, String.class);
		//assertNotNull(response.getHeaders().get("Location"));
		//assertThat(response.getHeaders().get("Location"), hasItem(startsWith("/v1/persons/")));
		// json-path
		System.out.println(response);
	}
	@Ignore
	@Test
	public void test2() {
		RestTemplate restTemplate = new RestTemplate();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON); // Set what you need

		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<ArrayList<Person1>> entity = new HttpEntity<ArrayList<Person1>>(headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/v1/persons", HttpMethod.GET,
				entity, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		System.out.println(response);
	}
	@Ignore
	@Test
	public void test4() {
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<Person1> response = restTemplate.getForObject("http://localhost:8080/v1/persons", ArrayList.class);
		assertNotNull(response);
	}
}
