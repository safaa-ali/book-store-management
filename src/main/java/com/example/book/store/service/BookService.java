package com.example.book.store.service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    void deleteBook(Long bookId);
    List<Book> getBookByCategoryKeyWord(Category category);
	Object greet();
}