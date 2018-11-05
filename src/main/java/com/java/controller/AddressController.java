package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.Address;
import com.java.service.AddressService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="v1/persons/{personId}/addresses")
public class AddressController {

	@Autowired AddressService service;
	
	@GetMapping(path = "/{hno}",  produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	
	public ResponseEntity<Address> fetchById(@ApiParam(name="hno", value="Represents house number") @PathVariable("hno") int id, @PathVariable("personId") int personId) {
		Address address=service.findByHno(id, personId);
		if(address==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(address);
	 }
	
	@PutMapping( consumes = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Integer> updateAddress(@ApiParam(value="Person id, unique identifier for person")@PathVariable("personId") int personId,@RequestBody Address address) {
		MultiValueMap<String, String> header = new HttpHeaders();
		System.out.println(address);
		return new ResponseEntity(service.saveAddress(personId, address), header, HttpStatus.CREATED);
	}
}
