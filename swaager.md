To add it to our Maven project, we need a dependency in the pom.xml file.

<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.9.2</version>
		</dependency>
		
		UI
		
<dependency>
<groupId>io.springfox</groupId>
<artifactId>springfox-swagger-ui</artifactId>
<version>2.9.2</version>
</dependency>

@EnableSwagger2

 @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    

It is not always desirable to expose the documentation for your entire API. You can restrict Swagger’s response by passing parameters to the apis() and paths() methods of the Docket class.

As seen above, RequestHandlerSelectors allows using the any or none predicates, but can also be used to filter the API according to the base package, class annotation, and method annotations.

PathSelectors provides additional filtering with predicates which scan the request paths of your application. You can use any(), none(), regex(), or ant().

In the example below, we will instruct Swagger to include only controllers from a particular package, with specific paths, using the ant() predicate.


@Bean
public Docket api() {                
    return new Docket(DocumentationType.SWAGGER_2)          
      .select()                                       
      .apis(RequestHandlerSelectors.basePackage("org.baeldung.web.controller"))
      .paths(PathSelectors.ant("/foos/*"))                     
      .build();
}
    Swagger 2 is enabled through the @EnableSwagger2 annotation.

After the Docket bean is defined, its select() method returns an instance of ApiSelectorBuilder, which provides a way to control the endpoints exposed by Swagger.

Predicates for selection of RequestHandlers can be configured with the help of RequestHandlerSelectors and PathSelectors. Using any() for both will
 make documentation for your entire API available through Swagger.
 
 To verify that Springfox is working, you can visit the following URL in your browser:

http://localhost:8080/v2/api-docs
The result is a JSON response with a large number of key-value pairs, which is not very human-readable. Fortunately, Swagger provides Swagger UI for this purpose.

Swagger UI is a built-in solution which makes user interaction with the Swagger-generated API documentation much easier.

5.1. Enabling Springfox’s Swagger UI
To use Swagger UI, one additional Maven dependency is required:

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>

Now you can test it in your browser by visiting http://localhost:8080/swagger-ui.html

In our case, by the way, the exact URL will be: http://localhost:8080/spring-security-rest/api/swagger-ui.html


https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
