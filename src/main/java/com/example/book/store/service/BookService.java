package com.example.book.store.service;


import java.util.List;

import com.example.book.store.entity.Book;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    void deleteBook(Long bookId);
	Book getBookByGenre(String genre);

}