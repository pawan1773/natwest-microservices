# Book Price Microservices

## Introduction
This is a sample microservices project.

## Technology
 * Java 11
 * SpringBoot 2.3.1
 
## Flow
 * Routes are configured inside ``src/main/java/com/skyskraper/routes`` package
 * Flow goes like ``RestApiRouteBuilder.java`` &#8594; ``DatabaseRouteBuilder.java`` &#8594; ``TransformerRouteBuilder.java`` &#8594; ``KafkaRouteBuilder.java``
 * Route exception handlers are configured inside ``src/main/java/com/skyskraper/routes/GlobalErrorHandlerRouteBuilder.java``
 * ``src/main/resources/data.sql`` contains scripts to insert dummy data and automatically executed on application startup. You can remove it if not needed.

## Quick Start
1. Install and start Oracle 18c database server. See instructions [here](https://docs.oracle.com/en/database/oracle/oracle-database/18/install-and-upgrade.html) 
2. Download Apache Kafka 2.6.0 setup and start ZooKeeper and Kafka broker services. See instructions [here](https://kafka.apache.org/quickstart) 
2. Edit below database properties inside ``application.yml`` file or via environment variables. Refer to comments below for better understanding

   ```
    db:
     # The host of the database server. It can be customized using
     # the 'DB_HOST' environment variable
     host: <hostname e.g. localhost or server IP>
     # The port of the database server. It can be customized using
     # the 'DB_PORT' environment variable
     port: <port_number>
     # The sid  or service name of the data server. It can be customized using
     # the 'DB_SID' environment variable
     sid: <sid>
     # The database username. It can be customized using the 'DB_USERNAME'
     # environment variable
     username: <your_username>
     # The database password. It can be customized using the 'DB_PASSWORD'
     # environment variable
     password: <your_password>
   ```
3. Edit kafka properties in ``application.yml`` file

	```
	kafka:
	  brokers: localhost:9092
     retries: 1
     group-id: 1
     bridge-error-handler: true
     heartbeat-interval-ms: 3000
     
	skyscraper:
	  kafka:
	    topic: test-topic
	```
4. You can update below property as per your requirement. Possible values are ``validate | update | create | create-drop``. See [here](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl) for more information

   ```
    hibernate:
      # To be updated in real production usage
      ddl-auto: create-drop
   ```

## Build
1. Building with Apache Maven

    ```
    mvn build
    ```
    
2. The Oracle JDBC drivers are not in public Maven repositories due to legal restrictions. So, if you are in a company, chances are you will have a Nexus installation with the Oracle JDBC jar installed. In case, build is getting failed due to ojbc jar unavailabilty, you might need to download and install into your Maven repository using below Maven command. 

	```
	mvn install:install-file -Dfile=<path_of_jar_on_system> -DgroupId=com.oracle.jdbc -DartifactId=ojdbc8 -Dversion=19.3.0.0 -Dpackaging=jar
	```
    
## Run
1. If you are using an IDE, then you can simply run it as Springboot application
2. Running the application with Apache Maven

	```
	mvn spring-boot:run
	```
3. By default, application starts at port ``8080``. You can change port using VM argument ``-Dserver.port=<your_port_number>`` or set in ``application.yml`` file by editing below property


	```
	# To configure server port
    server:
      port: 8080
	```
	
## Endpoints
1. ``http://localhost:8080/cs_01/?LAST_UPDATED_DATE=2020-01-01`` Note, supported ``LAST_UPDATED_DATE`` format is ``yyyy-MM-dd``
2. Endpoint to api docs - ``http://localhost:8080/api-doc``
	
## Validation
1. Refer to ``Validation.md`` to learn how to validate functionality.

## Notes

1. When Kafka is unavailable or an error occurrs while publishing to Kafka, application throws exception message like "Topic topic-name not present in metadata after 60000 ms". User will get an HTTP response with an error message. In case, topic is not present, application will automatically create the topic. 
	
	
	 
