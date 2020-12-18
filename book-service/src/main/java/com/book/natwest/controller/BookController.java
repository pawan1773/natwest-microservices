package com.book.natwest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	@LoadBalanced
	RestTemplate restTemplate;
	
	@GetMapping("/hello")
	public String hello() {
		ResponseEntity<String> hello = restTemplate.exchange("http://BOOK-PRICE-SERVICE/price", HttpMethod.GET, null, String.class);
		return hello.getBody();
	}

}
