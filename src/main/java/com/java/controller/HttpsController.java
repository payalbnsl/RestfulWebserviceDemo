package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HttpsController {
	
	@Autowired
	RestTemplate template;
	
	@GetMapping("/demo-one")
	public String getData() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> result = template.exchange("https://jsonplaceholder.typicode.com/todos/1",
				HttpMethod.GET, entity, String.class);
		return result.getBody();
	}
}
