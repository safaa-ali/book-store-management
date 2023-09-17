package com.example.book.store;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;
import com.example.book.store.service.BookService;

@SpringBootTest()
class BookStoreApplicationTests{

	@Autowired
	private BookService bookService;

	@Test
	void savedBook() {
		Book book = new Book(57l, "title", "auther", "discription", Category.ACTION, 100);
		Book savedBook = bookService.createBook(book);
		assertEquals(savedBook.getTitle(), "title");
	}

}
