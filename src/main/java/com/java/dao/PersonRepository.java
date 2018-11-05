package com.java.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.dto.Address;
import com.java.dto.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person>{

	@Query("select p.address from Person p left outer join p.address where p.id= ?1 and p.address.hno= ?2 ")
	public Address findByAddress_HNo(int id, int hNo);
	

	@Modifying
	@Query(value = "update Person set address.hno= :hno, address.city= :city, address.pincode= :pincode where id= :personId")
	public int saveAddress(@Param("personId") int personId,
			@Param("hno") int hno, @Param("city") String city,
			@Param("pincode") long pincode);
	
	@Query(nativeQuery=true, value="select 2*3")
	public int checkHealth();
	
	
/*
	@Modifying
	@Query(value = "update Person set address=:address where id= :personId")
	public int saveAddress1(@Param("personId") int personId, @Param("address") Address address);
*/


	public Optional<Person> findByName(String name);


}
