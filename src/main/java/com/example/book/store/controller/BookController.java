package com.example.book.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;
import com.example.book.store.service.BookService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/books")
public class BookController {

	@Autowired
	private BookService bookService;

// done
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookService.createBook(book);
		return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	}

// done
	@GetMapping("/getBookById/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {
		Book book = bookService.getBookById(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

// done
	@GetMapping("/getAllBooks")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> bookList = bookService.getAllBooks();
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}

// done
	@PutMapping("/updateBook/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long bookId, @RequestBody Book book) {
		book.setId(bookId);
		Book updatedBook = bookService.updateBook(book);
		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
	}

// done
	@DeleteMapping("/deleteBook/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>("Book was deleted successfully", HttpStatus.OK);
	}

// done
	@GetMapping("/getbooksByCategory")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Book> getBookByCategoryKeyWord(@RequestParam Category category) {
		return bookService.getBookByCategoryKeyWord(category);
	}

	// done
	@GetMapping("/{id}/borrow")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<String> borrowBook(@PathVariable Long id) {
		LocalDate localDate = LocalDate.now().plusDays(7);

		ResponseEntity<Book> exsitBook = getBookById(id);

		String title = exsitBook.getBody().getTitle();

		List<Book> books = getAllBooks().getBody().stream().filter(s -> s.getTitle().equals(title))
				.collect(Collectors.toList());

		if (books.size() > 2) {
			return new ResponseEntity<>("you can borrow it at " + localDate, HttpStatus.OK);
		}

		String message = "Unfortunately, we only have two copies, so you cannot borrow at this time so please try in anther time ! ";

		return new ResponseEntity<>(message.toUpperCase(), HttpStatus.OK);

	}

}