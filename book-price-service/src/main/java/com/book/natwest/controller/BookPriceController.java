package com.book.natwest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/price")
public class BookPriceController {

	@GetMapping
	public String getPrice() {
		return "Hello from Book Price Service";
	}
}
