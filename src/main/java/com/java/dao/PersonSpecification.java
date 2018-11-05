package com.java.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.java.dto.Person;

public class PersonSpecification implements Specification<Person>{

	int id;
	List<String> list= new ArrayList<>();
	
	public PersonSpecification(int id, List<String> list){
		this.id= id;
		this.list= list;
	}
	
	private static final long serialVersionUID = -251422912290534703L;

		@Override
		public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Predicate p1= builder.le(root.get("id"), id);
			Predicate p2= builder.in(root.get("address.state").in(list));
			return builder.and(p1, p2);
		}
}
