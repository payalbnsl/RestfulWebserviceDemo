package com.java.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Person2PersonMapper {

	public static Person getObject(Person1 obj) {
		Person p= new Person();
		if(obj==null) {
			return p;
		}
		p.setPersonId(obj.getPersonId());
		p.setAddress(obj.getAddress());
		p.setEmailId(obj.getEmailId());
		p.setName(obj.getName());
		Map<Type, Long> map= new HashMap<>();
		for(Long l: obj.getPhoneNumber()) {
			
			map.put(Type.NA, l);
		}
		p.setPhoneNumber(map);
		return p;
	}
	

	public static Person1 getObject(Person obj) {
		Person1 p= new Person1();
		if(obj==null) {
			return p;
		}
		p.setPersonId(obj.getPersonId());
		p.setAddress(obj.getAddress());
		p.setEmailId(obj.getEmailId());
		p.setName(obj.getName());
		List< Long> list= new ArrayList<>();
		Map<Type, Long> map=obj.getPhoneNumber();
			list.addAll(map.values());
		p.setPhoneNumber(list);
		return p;
	}
}
