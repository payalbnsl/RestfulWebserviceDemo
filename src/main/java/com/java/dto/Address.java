package com.java.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.hateoas.ResourceSupport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@ApiModel(description="Class representing address of a person")
public class Address extends ResourceSupport implements Serializable{
	@ApiModelProperty(notes="House number")
	private int hno;
	private String city;
	private String state;//{california, illinois} | id <20, order by city
	private long pincode;
}
