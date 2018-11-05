package com.java.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.dao.PersonRepository;
import com.java.dao.PersonSpecification;
import com.java.dto.Person;

@Service
@Transactional
public class PersonService {

	@Autowired PersonRepository rep;
	
	public Optional<Person> getOne(int id) {
		return rep.findById(id);
	}

	public List<Person> findAll() {
		return rep.findAll();
	}

	public Person updatePerson( Person person) {
		return rep.save(person);
		
	}

	public void deletePerson(int id) {
		rep.deleteById(id);
		
	}

	public Person savePerson(Person person) {
		return rep.save(person);
		
	}

	public Optional<Person> getByName(String name) {
		return rep.findByName(name);
	}

	public Page<Person> getByName(int offset, int limit) {
		return rep.findAll(PageRequest.of(offset, limit).first());
	}
	
	public List<Person> getBySpec(int id, String[] states) {
		return rep.findAll(new PersonSpecification(id, Arrays.asList(states)));
	}
}
