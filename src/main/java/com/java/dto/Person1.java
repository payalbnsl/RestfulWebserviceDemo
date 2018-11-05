package com.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person1 extends ResourceSupport{
	private int personId;
	@NotEmpty
	private String name;
	private List<Long> phoneNumber= new ArrayList<>();
	private String emailId;
	private Address address;
	
} 



