package com.example.book.store.mockito;

//import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.book.store.controller.BookController;
import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;
import com.example.book.store.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@RequestMapping("/api/test")

public  class BookControllerTest {
	
	
	

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;
	
	  @Autowired
	  private BookController bookController;

	@Autowired
	private ObjectMapper objectMapper;
	
	 @Configuration
     @Import(BookController.class) // A @Component injected with ExampleService
     static class Config {
     }
	 

	 @Test
	 @WithMockUser(username = "admin", password = "test", roles = "admin")

	public void givenBookObject_whenCreateEmployee_thenReturnSavedBook()  {
		ResultActions response = null;


		// given - precondition or setup
		Book book = Book.builder().title("harry poter").author("Fadatare").description("fantacy novel").price(100f)
				.category(Category.LITERATURE).build();
		given(bookService.createBook(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or behaviour that we are going test
		try {
			response = mockMvc.perform(post("/api/books/create")
//					.header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NTczNDI3MSwiZXhwIjoxNjk1ODIwNjcxfQ.bnvN9k7aZPHB7r6NfYQS4FrE0r-qvq4_FWsw83DCtaI")
					.content(objectMapper.writeValueAsString(book)));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// then - verify the result or output using assert statements

		try {
			response.andDo(print())
			.andExpect(status().isForbidden());
			
//			.andExpect(jsonPath("$.title", is(book.getTitle())))
//					.andExpect(jsonPath("$.author", is(book.getAuthor())))
//					.andExpect(jsonPath("$.description", is(book.getDescription())))
//					.andExpect(jsonPath("$.price", is(book.getPrice())))
//					.andExpect(jsonPath("$.category", is(book.getCategory())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	  @Test
	     public void testUserOfService() {
	         String actual = this.bookController.makeUse();
	         assertEquals("hello world", actual);
	     }

	    
	     
	     
	     
	     

}