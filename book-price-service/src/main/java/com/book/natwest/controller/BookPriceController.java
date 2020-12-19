package com.book.natwest.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.book.natwest.service.BookPriceService;

@RestController
public class BookPriceController {
	
	private final BookPriceService bookService;

	@Autowired
	public BookPriceController(final BookPriceService bookService) {
		this.bookService = bookService;
	}

	@GetMapping(value = "/price/{price}/quantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, BigDecimal>> getBookPrice(@PathVariable("price") BigDecimal price,
			@PathVariable("quantity") Integer quantity) {
		return ResponseEntity.ok(bookService.getBookPrice(price, quantity));
	}
}
