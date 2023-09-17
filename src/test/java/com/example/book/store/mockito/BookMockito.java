package com.example.book.store.mockito;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.book.store.BookStoreApplication;
import com.example.book.store.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BookStoreApplication.class)
@WebAppConfiguration

public abstract class  BookMockito {
	

	
	

		protected MockMvc mvc;
		@Autowired
		WebApplicationContext webApplicationContext;

		protected void setUp() {
			mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		}

		protected String mapToJson(Object obj) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		}

		protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException, JsonProcessingException {

			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, clazz);
		}
}