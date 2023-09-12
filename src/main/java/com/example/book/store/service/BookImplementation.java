package com.example.book.store.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.dao.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookImplementation implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
      return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long bookId) {
      Optional<Book> optionalBook = bookRepository.findById(bookId);
      return optionalBook.get();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
      Book existingBook = bookRepository.findById(book.getId()).get();
      existingBook.setTitle(book.getTitle());
      existingBook.setAuthor(book.getAuthor());
      existingBook.setDescription(book.getDescription());
      existingBook.setGenre(book.getGenre());
      Book updatedBook = bookRepository.save(existingBook);
      return updatedBook;
    }

    @Override
    public void deleteBook(Long bookId) {
       bookRepository.deleteById(bookId);
    }

	@Override
	public Book getBookByGenre(String genre) {
		 Optional<Book> optionalBook = bookRepository.findBookByGenre(genre);
	      return optionalBook.get();
	}
}