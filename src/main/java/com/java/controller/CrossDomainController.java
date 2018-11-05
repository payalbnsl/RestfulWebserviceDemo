package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
public class CrossDomainController {

	@Autowired
	RestTemplate template;

	// origin: domain: *: all domains are allowed to call this api
	//Access-control-allow-origin: *
	@GetMapping("/demo")
	public String getData() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> result = template.exchange("http://www.thomas-bayer.com/sqlrest/CUSTOMER/18/",
				HttpMethod.GET, entity, String.class);
		return result.getBody();
	}
	//preflight request
	//header: date
	
}
