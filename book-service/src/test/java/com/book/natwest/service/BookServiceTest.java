package com.book.natwest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.book.natwest.dto.BookDto;
import com.book.natwest.entity.Book;
import com.book.natwest.exception.BookNotFoundException;
import com.book.natwest.repository.BookRepository;

class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private BookRepository bookRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldAddBook() {
		final BookDto bookDto = new BookDto();
		bookDto.setName("Java in action");
		bookDto.setPrice(new BigDecimal(139.99));

		final Book book = new Book();
		book.setName("Java in action");
		book.setPrice(new BigDecimal(139.99));

		when(bookRepository.save(book)).thenAnswer(invocation -> invocation.getArgument(0));

		final BookDto addedBook = bookService.addBook(bookDto);

		assertNotNull(addedBook);

		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void shouldAddMultipleBook() {
		final BookDto bookDto = new BookDto();
		bookDto.setName("Java in action");
		bookDto.setPrice(new BigDecimal(139.99));

		final List<BookDto> bookDtos = new ArrayList<>();
		bookDtos.add(bookDto);

		final Book book = new Book();
		book.setName("Java in action");
		book.setPrice(new BigDecimal(139.99));

		final List<Book> books = new ArrayList<>();
		books.add(book);

		when(bookRepository.saveAll(books)).thenAnswer(invocation -> invocation.getArgument(0));

		final List<BookDto> addedBooks = bookService.addBooks(bookDtos);

		assertNotNull(addedBooks);

		assertEquals(1, addedBooks.size());

		verify(bookRepository, times(1)).saveAll(any());
	}

	@Test
	void shouldReturnBook() {

		final Long id = 1L;

		final Book book = new Book();
		book.setId(id);
		book.setName("Java in action");
		book.setPrice(new BigDecimal(139.99));

		when(bookRepository.findById(id)).thenReturn(Optional.of(book));

		final BookDto bookDto = bookService.getBook(id);

		assertNotNull(bookDto);

		verify(bookRepository, times(1)).findById(anyLong());
	}

	@Test
	void shouldThrowBookNotFoundException() {
		final Long id = 1L;

		when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(null));

		when(bookRepository.findAll()).thenReturn(List.of());

		assertThrows(BookNotFoundException.class, () -> {
			bookService.getBook(id);
		});

		assertThrows(BookNotFoundException.class, () -> {
			bookService.getBooks();
		});
	}

	@Test
	void shouldReturnListOfBooks() {
		final Long id = 1L;

		final Book book = new Book();
		book.setId(id);
		book.setName("Java in action");
		book.setPrice(new BigDecimal(139.99));

		final List<Book> books = new ArrayList<>();
		books.add(book);

		when(bookRepository.findAll()).thenReturn(books);

		final List<BookDto> bookDtos = bookService.getBooks();

		assertNotNull(bookDtos);

		verify(bookRepository, times(1)).findAll();
	}
}
