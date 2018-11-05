Spring MVC provides @CrossOrigin annotation. This annotation marks the annotated method or type as permitting cross origin requests.

1.1. Spring CORS allow all
By default, @CrossOrigin allows all origins, all headers, the HTTP methods specified in the @RequestMapping annotation and a maxAge of 30 minutes.

You can override default CORS settings by giving value to annotation attributes :

ATTRIBUTE	DESCRIPTION
origins	List of allowed origins. It’s value is placed in the Access-Control-Allow-Origin header of both the pre-flight response and the actual response.
– * – means that all origins are allowed.
– If undefined, all origins are allowed.
allowedHeaders	List of request headers that can be used during the actual request. Value is used in preflight’s response header Access-Control-Allow-Headers.
– * – means that all headers requested by the client are allowed.
– If undefined, all requested headers are allowed.
methods	List of supported HTTP request methods. If undefined, methods defined by RequestMapping annotation are used.
exposedHeaders	List of response headers that the browser will allow the client to access. Value is set in actual response header Access-Control-Expose-Headers.
– If undefined, an empty exposed header list is used.
allowCredentials	It determine whether browser should include any cookies associated with the request.
– false – cookies should not included.
– "" (empty string) – means undefined.
– true – pre-flight response will include the header Access-Control-Allow-Credentials with value set to true.
– If undefined, credentials are allowed.
maxAge	maximum age (in seconds) of the cache duration for pre-flight responses. Value is set in header Access-Control-Max-Age.
– If undefined, max age is set to 1800 seconds (30 minutes).
1.2. @CrossOrigin at Class/Controller Level
HomeController.java
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HomeController
{
    @GetMapping(path="/")
    public String homeInit(Model model) {
        return "home";
    }
}
Read More – Spring 5 MVC Example

1.3. @CrossOrigin at Method Level
HomeController.java
@Controller
public class HomeController
{
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path="/")
    public String homeInit(Model model) {
        return "home";
    }
}
1.4. @CrossOrigin Overridden at Method Level
homeInit() method will be accessible only from domain http://example.com. Rest other methods in HomeController will be accessible from all domains.

HomeController.java
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController
{
    @CrossOrigin(origins = "http://example.com")
    @GetMapping(path="/")
    public String homeInit(Model model) {
        return "home";
    }
}
2. Spring CORS – Global CORS configuration
2.1. Spring MVC CORS with WebMvcConfigurerAdapter
To enable CORS for the whole application, use WebMvcConfigurerAdapter to add CorsRegistry.

CorsConfiguration.java
@Configuration
@EnableWebMvc
public class CorsConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST");
    }
}
2.2. Spring Boot CORS with WebMvcConfigurer
In spring boot application, it is recommended to just declare a WebMvcConfigurer bean.

CorsConfiguration.java
@Configuration
public class CorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
2.3. CORS with Spring Security
To enable CORS support through Spring security, configure CorsConfigurationSource bean and use HttpSecurity.cors() configuration.

WebSecurityConfig.java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            //other config
    }
 
    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


This minimal XML configuration enables CORS on a /** path pattern with the same default properties as the JavaConfig one:


<mvc:cors>
    <mvc:mapping path="/**" />
</mvc:cors>
It is also possible to declare several CORS mappings with customized properties:


<mvc:cors>
 
    <mvc:mapping path="/api/**"
        allowed-origins="http://domain1.com, http://domain2.com"
        allowed-methods="GET, PUT"
        allowed-headers="header1, header2, header3"
        exposed-headers="header1, header2" allow-credentials="false"
        max-age="123" />
 
    <mvc:mapping path="/resources/**"
        allowed-origins="http://domain1.com" />
 
</mvc:cors>


CORS requests are automatically dispatched to the various HandlerMappings that are registered. They handle CORS preflight requests and intercept CORS simple and actual requests by means of a CorsProcessor implementation (DefaultCorsProcessor by default) in order to add the relevant CORS response headers (such as Access-Control-Allow-Origin).

CorsConfiguration allows one to specify how the CORS requests should be processed: allowed origins, headers and methods among others. This may be provided in various ways:

AbstractHandlerMapping#setCorsConfiguration() allows one to specify a Map with several CorsConfigurations mapped onto path patterns such as /api/**
Subclasses may provide their own CorsConfiguration by overriding the AbstractHandlerMapping#getCorsConfiguration(Object, HttpServletRequest) method
Handlers may implement the CorsConfigurationSource interface (like ResourceHttpRequestHandler now does) in order to provide a CorsConfiguration for each request

The same-origin policy is an important security concept implemented by web browsers to prevent Javascript code from making requests against a different origin (e.g., different domain) than the one from which it was served. Although the same-origin policy is effective in preventing resources from different origins, it also prevents legitimate interactions between a server and clients of a known and trusted origin.

Cross-Origin Resource Sharing (CORS) is a technique for relaxing the same-origin policy, allowing Javascript on a web page to consume a REST API served from a different origin.

The same-origin policy is an important security concept implemented by web browsers to prevent Javascript code from making requests against a different origin (e.g., different domain) than the one from which it was served. Although the same-origin policy is effective in preventing resources from different origins, it also prevents legitimate interactions between a server and clients of a known and trusted origin.

Cross-Origin Resource Sharing (CORS) is a technique for relaxing the same-origin policy, allowing Javascript on a web page to consume a REST API served from a different origin.

Handling Simple CORS requests
In the simplest scenario, cross-origin communications starts with a client making a GET, POST, or HEAD request against a resource on the server. In this scenario, the content type of a POST request is limited to application/x-www-form-urlencoded, multipart/form-data, or text/plain. The request includes an Origin header that indicates the origin of the client code.

The server will consider the request's Origin and either allow or disallow the request. If the server allows the request, then it will respond with the requested resource and an Access-Control-Allow-Origin header in the response. This header will indicate to the client which client origins will be allowed to access the resource. Assuming that the Access-Control-Allow-Origin header matches the request's Origin, the browser will allow the request.

On the other hand, if Access-Control-Allow-Origin is missing in the response or if it doesn't match the request's Origin, the browser will disallow the request.

For example, suppose that client code served from foo.client.com were to send the following request for a resource at bar.server.com:

GET /greeting/ HTTP/1.1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/536.30.1 (KHTML, like Gecko) Version/6.0.5 Safari/536.30.1
Accept: application/json, text/plain, */*
Referer: http://foo.client.com/
Origin: http://foo.client.com
The Origin header tells the server that the client code originated from http://foo.client.com. So it checks its same-origin policies and determines that it can serve the request. The response might look like this:

HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Date: Wed, 20 Nov 2013 19:36:00 GMT
Server: Apache-Coyote/1.1
Content-Length: 35
Connection: keep-alive
Access-Control-Allow-Origin: http://foo.client.com

[response payload]
The Access-Control-Allow-Origin indicates that "http://foo.client.com" is allowed access, but no other origins will be allowed access.

Optionally, Access-Control-Allow-Origin may be set to "*" to indicate that all client origins are allowed. This is considered an unsafe practice, however, except in special cases where an API is completely public and is expected to be consumed by any client.

Pre-flight requests
If a request may have implications on user data, a simple request is insufficient. Instead, a preflight CORS request is sent in advance of the actual request to ensure that the actual request is safe to send. Preflight requests are appropriate when the actual request is any HTTP Method other than GET, POST, or HEAD or if a POST request's content type is anything other than application/x-www-form-urlencoded, multipart/form-data, or text/plain. Also, if the request contains any custom headers, then a preflight request is required.

Note: Some Javascript libraries, such as AngularJS and Sencha Touch, send preflight requests for any kind of request. This approach is arguably safer, because it doesn't assume that a service adheres to HTTP method semantics (i.e., a GET endpoint could have been written to have side effects.)

For example, suppose that a client served from foo.client.com performs a DELETE request against a resource at bar.server.com. The preflight request takes the form of an OPTIONS request with the following headers:

OPTIONS /resource/12345
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/536.30.1 (KHTML, like Gecko) Version/6.0.5 Safari/536.30.1
Access-Control-Request-Method: DELETE
Access-Control-Request-Headers: origin, x-requested-with, accept
Origin: http://foo.client.com
The preflight request is essentially asking the server if it would allow the DELETE request, without actually sending the DELETE request. If the server allows the original request, then it will respond to the preflight request like this:

HTTP/1.1 200 OK
Date: Wed, 20 Nov 2013 19:36:00 GMT
Server: Apache-Coyote/1.1
Content-Length: 0
Connection: keep-alive
Access-Control-Allow-Origin: http://foo.client.com
Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE
Access-Control-Max-Age: 86400
The response to the preflight request indicates (in the Access-Control-Allow-Methods header) that the client is allowed to issue a DELETE request for the given resource. The Access-Control-Max-Age indicates that this preflight response is good for 86,400 seconds, or 1 day, after which a new preflight request must be issued.

In the meantime, the client will be allowed to send the original DELETE request for the resource.
