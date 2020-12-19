package com.book.natwest.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.natwest.dto.BookDto;
import com.book.natwest.service.BookService;

@RestController
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(final BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookDto> addBook(@RequestBody final BookDto bookDto) {
		return ResponseEntity.ok(bookService.addBook(bookDto));
	}

	@PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookDto>> addBooks(@RequestBody final List<BookDto> bookDtos) {
		return ResponseEntity.ok(bookService.addBooks(bookDtos));
	}

	@GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookDto>> getBooks() {
		return ResponseEntity.ok(bookService.getBooks());
	}

	@GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookDto> getBooks(@PathVariable("id") Long id) {
		return ResponseEntity.ok(bookService.getBook(id));
	}

	@DeleteMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> deleteBook(@PathVariable("id") Long id) {
		return ResponseEntity.ok(bookService.deleteBook(id));
	}

	@GetMapping(value = "/book/{id}/quantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, BigDecimal>> getBookPrice(@PathVariable("id") Long id,
			@PathVariable("quantity") Integer quantity) {
		return ResponseEntity.ok(bookService.getBookPrice(id, quantity));
	}
}
