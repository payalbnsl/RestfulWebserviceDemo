package com.java.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class Address extends ResourceSupport implements Serializable{
	private int hno;
	private String city;
	private String state;//{california, illinois} | id <20, order by city
	private long pincode;
}
