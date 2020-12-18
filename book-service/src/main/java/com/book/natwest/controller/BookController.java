package com.book.natwest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

}
