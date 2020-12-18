package com.book.natwest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BookDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookDiscoveryServerApplication.class, args);
	}

}
