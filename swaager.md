To add it to our Maven project, we need a dependency in the pom.xml file.


<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
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
