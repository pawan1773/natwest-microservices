package com.book.natwest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class BookZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookZuulApiGatewayApplication.class, args);
	}

}