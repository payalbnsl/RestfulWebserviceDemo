package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.java.dao.PersonRepository;

@Component
public class MyHealthIndicator implements HealthIndicator{

	@Autowired PersonRepository rep;
	@Override
	public Health health() {
		int val=rep.checkHealth();
		if(val!=6) {
			return Health.outOfService().withDetail("Database status", "Database seems to be down").build();
		}
		return Health.up().build();
	}

}
