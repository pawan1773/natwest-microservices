# Sample Microservices Implementation

## Introduction
This is a sample microservices project.

## Technology
 * Java 11
 * SpringBoot 2.3.7.RELEASE
 * Spring Cloud Hoxton.SR9
 * H2 in-memory database
 
## Components
 * book-discovery-server - Eureka discovery server to register various microservices
 * book-zuul-api-gateway - Zuul API Gateway to act as a proxy layer between client and microservices
 * book-service - Exposes APIs to add, get, delete books and get total price
 * book-price-service - Contains logic to calculate total price of book depending on the quantity

## Build
1. Build all the components with Apache Maven

    ```
    mvn clean compile package
    ```
	
## Test
1. Test case are written for ``book-service``. Run below command to execute test cases

    ```
    mvn test
    ```
    
## Run
1. If you are using an IDE, then you can simply run all the components Springboot application
2. Running the components with Apache Maven

	```
	mvn spring-boot:run
	```

	
## Validation
1. Open ``http://localhost:8761/`` in browser to see if all the services are up and running
2. Import ``book-microservice.postman_collection.json`` in Postman.
3. Hit and check responses of all the APIs.
4. Modify request body and parameters if needed

