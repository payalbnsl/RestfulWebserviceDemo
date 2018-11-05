/*package com.java.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.dto.Person;

@Repository
public class DemoRepository {

	@Autowired EntityManagerFactory emf;
	
	public List<Person> getByCriteria(){
		CriteriaBuilder builder=emf.getCriteriaBuilder();
		CriteriaQuery<Person> query=builder.createQuery(Person.class);
		Root<Person> root=query.from(Person.class);
		Predicate idLessThanEqual20=builder.le(root.get("id"), 20);
		Predicate stateMatch=builder.in(root.get("address.state").in(Arrays.asList("Illinois", "California")));
		Predicate andCondition=builder.and(idLessThanEqual20, stateMatch);
		query.where(andCondition).orderBy(builder.asc(root.get("address.city")));
		return emf.createEntityManager().createQuery(query.select(root)).getResultList();
	}
}
*/