package com.book.natwest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * This is a Zuul API gateway that acts as a proxy server for several
 * microservices.
 * 
 * @author joginder.pawan@gmail.com
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class BookZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookZuulApiGatewayApplication.class, args);
	}

}
