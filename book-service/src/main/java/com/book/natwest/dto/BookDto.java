package com.book.natwest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable {
	
	private static final long serialVersionUID = -3556970635673130836L;

	private Long id;
	
	private String name;
	
	private BigDecimal price;
}
