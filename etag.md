  An ETag (entity tag) is an HTTP response header returned by an HTTP/1.1 compliant web server used to determine change in content at a given URL.

ETags are used for two things – caching and conditional requests. The ETag value can be though as a hash computed out of the bytes of the Response body. Because a cryptographic hash function is likely used, even the smallest modification of the body will drastically change the output and thus the value of the ETag. This is only true for strong ETags – the protocol does provide a weak Etag as well.

Using an If-* header turns a standard GET request into a conditional GET. The two If-* headers that are using with ETags are “If-None-Match” and “If-Match” – each with it’s own semantics

ETag support in Spring
On to the Spring support – to use ETag in Spring is extremely easy to set up and completely transparent for the application. The support is enabled by adding a simple Filter in the web.xml:

<filter>
   <filter-name>etagFilter</filter-name>
   <filter-class>org.springframework.web.filter.ShallowEtagHeaderFilter</filter-class>
</filter>
<filter-mapping>
   <filter-name>etagFilter</filter-name>
   <url-pattern>/api/*</url-pattern>
</filter-mapping>
The filter is mapped on the same URI pattern as the RESTful API itself. The filter itself is the standard implementation of ETag functionality since Spring 3.0.


   @Bean
    public Filter filter(){
        ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
        return filter;
    }
    
    
    
