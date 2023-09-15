package com.example.book.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;
import com.example.book.store.entity.dao.BookRepository;
import com.example.book.store.exceptions.BookNotFoundException;
import com.example.book.store.exceptions.DuplicateResourceException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookStoreServiceImpl implements BookService {

	private static final Logger log = LoggerFactory.getLogger(BookStoreServiceImpl.class);

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
		existingBook.setCategory(book.getCategory());
		Book updatedBook = bookRepository.save(existingBook);
		return updatedBook;
	}

	@Override
	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}

	@Override
	public List<Book> getBookByCategoryKeyWord(Category category) {

		log.info("Fetch all the books by category .");
		List<Book> book = bookRepository.findAllBookByCategoryAndKeyword(category.getValue());
		return book;
	}

}