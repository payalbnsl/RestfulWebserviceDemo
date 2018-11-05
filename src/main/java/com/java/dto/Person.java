package com.java.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Person extends ResourceSupport implements Serializable{
	@GeneratedValue
	@Id
	private int personId;
	@NotEmpty
	private String name;
	@ElementCollection
	@OrderColumn(name="index")
	private Map<Type, Long> phoneNumber= new HashMap<>();
	@Email
	private String emailId;
	@Embedded
	private Address address;
	@Version
	private int version;
	
	public Person(String name,Map<Type, Long> phoneNumber, String emailId,Address address) {
		this.name= name;
		this.phoneNumber= phoneNumber;
		this.emailId= emailId;
		this.address= address;
	}
} 


