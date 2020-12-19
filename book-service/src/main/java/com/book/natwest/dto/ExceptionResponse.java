package com.book.natwest.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
	
	private static final long serialVersionUID = 6578671204373505432L;
	
	private LocalDateTime timestamp;
	
	private String message;
	
	private String details;
}
