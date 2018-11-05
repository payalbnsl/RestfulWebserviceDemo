package com.java.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import com.java.dto.Person;

@Endpoint(id="person-info")
public class PersonsEndpoint {

	private Map<Integer, Person> persons= new HashMap<>();
	public PersonsEndpoint(){
		persons.put(1, new Person("Payal", null, "payal@rjt", null));
	}
	
	@ReadOperation
	public Collection<Person> getAll() {
		return persons.values();
	}
	//@WriteOperation
	//@DeleteOperation
}
