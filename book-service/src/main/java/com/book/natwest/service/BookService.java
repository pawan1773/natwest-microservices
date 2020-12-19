package com.book.natwest.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.book.natwest.dto.BookDto;
import com.book.natwest.entity.Book;
import com.book.natwest.exception.BookNotFoundException;
import com.book.natwest.repository.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@LoadBalanced
	private final RestTemplate restTemplate;

	@Autowired
	public BookService(final BookRepository bookRepository, final RestTemplate restTemplate) {
		this.bookRepository = bookRepository;
		this.restTemplate = restTemplate;
	}

	public BookDto addBook(final BookDto bookDto) {
		Book book = new Book();
		BeanUtils.copyProperties(bookDto, book, "id");

		book = bookRepository.save(book);

		final BookDto savedBook = new BookDto();
		BeanUtils.copyProperties(book, savedBook);

		return savedBook;
	}

	public List<BookDto> getBooks() {
		final List<Book> books = bookRepository.findAll();

		if (books.isEmpty()) {
			throw new BookNotFoundException("No books found");
		}

		return books.stream().map(book -> new BookDto(book.getId(), book.getName(), book.getPrice()))
				.collect(Collectors.toList());
	}

	public BookDto getBook(final Long id) {
		final Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("No such book with id: " + id));
		final BookDto bookDto = new BookDto();
		BeanUtils.copyProperties(book, bookDto);
		return bookDto;
	}

	public List<BookDto> addBooks(final List<BookDto> bookDtos) {
		List<Book> books = bookDtos.stream().map(book -> new Book(book.getId(), book.getName(), book.getPrice()))
				.collect(Collectors.toList());

		books = bookRepository.saveAll(books);

		return books.stream().map(book -> new BookDto(book.getId(), book.getName(), book.getPrice()))
				.collect(Collectors.toList());
	}

	@Transactional
	@Modifying
	public Map<String, String> deleteBook(final Long id) {
		final Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("No such book with id: " + id));

		bookRepository.delete(book);

		final Map<String, String> response = new HashMap<>(1);
		response.put("message", "Removed book with id: " + id);
		return response;
	}

	public Map<String, BigDecimal> getBookPrice(final Long id, final Integer quantity) {
		final Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("No such book with id: " + id));

		final String url = "http://BOOK-PRICE-SERVICE/price/" + book.getPrice() + "/quantity/" + quantity;

		final ResponseEntity<Map<String, BigDecimal>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Map<String, BigDecimal>>() {
				});
		return response.getBody();
	}
}
