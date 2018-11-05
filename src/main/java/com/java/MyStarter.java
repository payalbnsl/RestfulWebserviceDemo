package com.java;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.Filter;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.java.controller.PersonsEndpoint;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.java.dao")
@EntityScan(basePackages = "com.java.dto")
@EnableTransactionManagement(proxyTargetClass = true)
@ManagementContextConfiguration
@EnableSwagger2
public class MyStarter {

	public static void main(String[] args) {
		SpringApplication.run(MyStarter.class, args);

	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	@Bean
	public PersonsEndpoint persons() {
		return new PersonsEndpoint();
	}

	@Bean
	public Filter getFilter() {
		return new ShallowEtagHeaderFilter();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfo("Person Directory Service",
						"This api gives information about people and their addresses", "2.0", "urn:tos",
						new Contact("Payal Bansal", "", "jahanvi.bansal@gmail.com").toString(), "Apache 2.0",
						"http://www.apache.org/licenses/LICENSE-2.0"))
				.produces(new HashSet(Arrays.asList("application/json", "application/xml")))
				.consumes(new HashSet(Arrays.asList("application/json", "application/xml"))).select()
				.apis(RequestHandlerSelectors.basePackage("com.java.controller")).paths(PathSelectors.any()).build();

	}

	@Bean
	public ServletWebServerFactory factory() {
		TomcatServletWebServerFactory factory= new TomcatServletWebServerFactory() {
			public void postProcessContext(Context context) {
				SecurityConstraint constraint= new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection= new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		factory.addAdditionalTomcatConnectors(createSSlConnector());
		return factory;
	}

	private Connector createSSlConnector() {
		Connector connector= new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setRedirectPort(8443);
		connector.setSecure(false);
		return connector;
	}
}
