package com.book.natwest.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BookPriceService {

	public Map<String, BigDecimal> getBookPrice(final BigDecimal price, final Integer quantity) {
        
		final Map<String, BigDecimal> response = new HashMap<>();
		response.put("total", price.multiply(new BigDecimal(quantity)));
        
		return response;
	}

}
