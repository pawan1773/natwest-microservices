package com.book.natwest.exception;

public class BookNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 6228873053002360980L;

	public BookNotFoundException(String message) {
		super(message);
	}

}
