package com.java;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.java.controller.PersonsEndpoint;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.java.dao")
@EntityScan(basePackages="com.java.dto")
@EnableTransactionManagement(proxyTargetClass=true)
@ManagementContextConfiguration
public class MyStarter {

	public static void main(String[] args) {
		SpringApplication.run(MyStarter.class, args);

	}

	@Bean
	public PersonsEndpoint persons() {
		return new PersonsEndpoint();
	}
	
	@Bean
	public Filter getFilter() {
		return new ShallowEtagHeaderFilter();
	}
}
