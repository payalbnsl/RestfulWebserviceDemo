package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.dao.PersonRepository;
import com.java.dto.Address;

@Service
@Transactional
public class AddressService {

	@Autowired PersonRepository rep;

	public Address findByHno(int id, int personId) {
		return rep.findByAddress_HNo(personId, id);
	}

	public int saveAddress(int personId, Address address) {
		return rep.saveAddress(personId, address.getHno(), address.getCity(), address.getPincode());
	}
	
	/*public int saveAddress1(int personId, Address address) {
		return rep.saveAddress(personId, address);
	}*/
	
}
