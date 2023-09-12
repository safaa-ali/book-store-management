package com.example.book.store.service;


import java.util.List;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.BookDto;
import com.example.book.store.entity.Category;

public interface BookService {
    Book createBook(Book book);
    void addNewBook(BookDto bookDto);
    void addBook(Long id, int quantityToAdd);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book updateBook(Book book);
    void deleteBook(Long bookId);
    List<Book> getBookByCategoryKeyWord(Category category);

    int getNumberOfBooksById(Long id);

}