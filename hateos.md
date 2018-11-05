/persons
/persons/{personid}/addresses

Restful little or  no documentation to share with the clients


Restful?

Richardson maturity model

1)Level0: If u have exposed ur all methods using just 1 url
/ProjectName xml data to this url (server: add method : 1,2 : int)
(soap webservice) 

2) Level1: create diff urls for every operations:
/getStudent
/deleteStudents
U have the operation info in the url. Not using http methods to specify operation u want to perform

3) Level2: Urls in the form of resources
and use proper http methods to specify what operation u want to perform
Restful

4) Level3: post operation: link to newly created resource
			get /persons : link to other resources /persons/{personid}/addresses
			
	Hateos: 	
	
Mature restful webservice	
			
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>

Entity classes should extend ResourceSupport

Link
		
