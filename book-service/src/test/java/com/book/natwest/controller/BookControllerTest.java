package com.book.natwest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.book.natwest.dto.BookDto;
import com.book.natwest.exception.BookNotFoundException;
import com.book.natwest.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Autowired
	private ObjectMapper objectMapper;

	private BookDto javaInAction;

	private BookDto springInAction;

	private BookDto javaInActionResponse;

	private BookDto springInActionResponse;

	@BeforeEach
	void setUp() {
		javaInAction = new BookDto();
		javaInAction.setName("Java in action");
		javaInAction.setPrice(new BigDecimal(139.99));

		springInAction = new BookDto();
		springInAction.setName("Spring in action");
		springInAction.setPrice(new BigDecimal(199.99));

		javaInActionResponse = new BookDto();
		BeanUtils.copyProperties(javaInAction, javaInActionResponse);
		javaInActionResponse.setId(1L);

		springInActionResponse = new BookDto();
		BeanUtils.copyProperties(javaInAction, springInActionResponse);
		springInActionResponse.setId(2L);
	}

	@Test
	void shouldAddBookWith200Status() throws JsonProcessingException, Exception {

		Mockito.when(bookService.addBook(javaInAction)).thenReturn(javaInActionResponse);

		this.mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(javaInAction)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(javaInActionResponse.getName())))
				.andExpect(jsonPath("$.price", is(javaInActionResponse.getPrice())));
	}

	@Test
	void shouldAddMultipleBooksWith200Status() throws JsonProcessingException, Exception {

		final List<BookDto> books = new ArrayList<>();
		books.add(javaInAction);
		books.add(springInAction);

		final List<BookDto> response = new ArrayList<>();
		response.add(javaInActionResponse);
		response.add(springInActionResponse);

		Mockito.when(bookService.addBooks(books)).thenReturn(response);

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/books").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(books)))
				.andExpect(status().isOk()).andReturn();

		final String responseContent = mvcResult.getResponse().getContentAsString();
		assertTrue(responseContent.contains(response.get(0).getName()));
		assertTrue(responseContent.contains(response.get(1).getName()));
	}

	@Test
	void shouldReturnMultipleBooksWith200Status() throws JsonProcessingException, Exception {

		final List<BookDto> response = new ArrayList<>();
		response.add(javaInActionResponse);
		response.add(springInActionResponse);

		Mockito.when(bookService.getBooks()).thenReturn(response);

		final MvcResult mvcResult = this.mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		final String responseContent = mvcResult.getResponse().getContentAsString();
		assertTrue(responseContent.contains(response.get(0).getName()));
		assertTrue(responseContent.contains(response.get(1).getName()));
	}

	@Test
	void shouldReturnNoBooksFoundMessageWith404Status() throws JsonProcessingException, Exception {

		final String message = "No books found";

		Mockito.when(bookService.getBooks()).thenThrow(new BookNotFoundException(message));

		final MvcResult mvcResult = this.mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();

		final String responseContent = mvcResult.getResponse().getContentAsString();
		assertTrue(responseContent.contains(message));
	}

	@Test
	void shouldReturnBookWith200Status() throws JsonProcessingException, Exception {

		Mockito.when(bookService.getBook(Mockito.anyLong())).thenReturn(javaInActionResponse);

		this.mockMvc.perform(get("/book/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(javaInActionResponse.getName())))
				.andExpect(jsonPath("$.price", is(javaInActionResponse.getPrice())));
	}

	@Test
	void shouldReturnNoSuchBookWithIdMessageWith404Status() throws JsonProcessingException, Exception {

		final String message = "No such book with id: 3";

		Mockito.when(bookService.getBook(Mockito.anyLong())).thenThrow(new BookNotFoundException(message));

		final MvcResult mvcResult = this.mockMvc.perform(get("/book/3").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();

		final String responseContent = mvcResult.getResponse().getContentAsString();
		assertTrue(responseContent.contains(message));
	}

	@Test
	void shouldReturnBookDeletedMessageWith200Status() throws JsonProcessingException, Exception {

		final String message = "Removed book with id: 1";

		final Map<String, String> response = new HashMap<>(1);
		response.put("message", message);

		Mockito.when(bookService.deleteBook(Mockito.anyLong())).thenReturn(response);

		this.mockMvc.perform(delete("/book/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(message)));
	}

	@Test
	void shouldReturnNoSuchBookWithIdWith404Status() throws JsonProcessingException, Exception {

		final String message = "No such book with id: 3";

		Mockito.when(bookService.deleteBook(Mockito.anyLong())).thenThrow(new BookNotFoundException(message));

		final MvcResult mvcResult = this.mockMvc
				.perform(delete("/book/3").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();

		final String responseContent = mvcResult.getResponse().getContentAsString();
		assertTrue(responseContent.contains(message));
	}

	@Test
	void shouldReturnPriceFor3BooksWith200Status() throws JsonProcessingException, Exception {

		final Integer quantity = 3;
		final Map<String, BigDecimal> response = new HashMap<>();
		response.put("total", new BigDecimal(199.33).multiply(new BigDecimal(quantity)));
		Mockito.when(bookService.getBookPrice(Mockito.anyLong(), Mockito.anyInt())).thenReturn(response);

		this.mockMvc.perform(get("/book/1/quantity/" + quantity).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.total", is(new BigDecimal(199.33).multiply(new BigDecimal(quantity)))));
	}

}
