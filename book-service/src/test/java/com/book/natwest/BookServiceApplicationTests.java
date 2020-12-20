package com.book.natwest;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "eureka.client.enabled:false" })
class BookServiceApplicationTests {

}
