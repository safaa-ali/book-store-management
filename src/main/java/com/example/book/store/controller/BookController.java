package com.example.book.store.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.Category;
import com.example.book.store.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookService.createBook(book);
		return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	}

	@GetMapping("/getBookById/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId) {

		try {

			if (bookService.getBookById(bookId).getId() != null) {

				Book book = bookService.getBookById(bookId);
				return new ResponseEntity<>(book, HttpStatus.OK);

			}
		} catch (Exception mes) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		return null;
	}

	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
//	@CachePut(value="getAllBooksCashing")

	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> bookList = bookService.getAllBooks();
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}

	@PutMapping("/updateBook/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long bookId, @RequestBody Book book) {
		book.setId(bookId);
		Book updatedBook = bookService.updateBook(book);
		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
	}

	@DeleteMapping("/deleteBook/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {

		try {

			if (getBookById(id).getBody().getId() != null) {

				bookService.deleteBook(id);
				return new ResponseEntity<>("Book was deleted successfully", HttpStatus.OK);

			}
		} catch (Exception mes) {

			return new ResponseEntity<>("Book doesn't exist with id: " + id, HttpStatus.NOT_FOUND);

		}
		return null;

	}

	@GetMapping("/getbooksByCategory")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Book> getBookByCategoryKeyWord(@RequestParam Category category) {

		try {

			List<Category> catrgories = getAllBooks().getBody().stream().map(s -> s.getCategory())
					.collect(Collectors.toList());
			;

			if (catrgories.contains(category)) {
				return bookService.getBookByCategoryKeyWord(category);

			}

		} catch (Exception mes) {

			new ResponseEntity<>("Book doesn't exist with id: " + category, HttpStatus.NOT_FOUND);

		}
		return null;

	}

	@GetMapping("/{id}/borrow")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<String> borrowBook(@PathVariable Long id) {

		LocalDate localDate = LocalDate.now().plusDays(7);
		
		try {

		if (getBookById(id).getBody().getId() != null) {

			ResponseEntity<Book> exsitBook = getBookById(id);

			String title = exsitBook.getBody().getTitle();

			List<Book> books = getAllBooks().getBody().stream().filter(s -> s.getTitle().equals(title))
					.collect(Collectors.toList());

			if (books.size() > 2) {
				return new ResponseEntity<>("you can borrow it at " + localDate, HttpStatus.OK);
			} else {

				String message = "Unfortunately, we only have two copies, so you cannot borrow at this time so please try in anther time ! ";
				return new ResponseEntity<>(message.toUpperCase(), HttpStatus.OK);
			}

		}
		}catch (Exception mes) {

			new ResponseEntity<>("Book doesn't exist with id: " + id, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<>("Book doesn't exist with id: " + id, HttpStatus.NOT_FOUND);

	}

	public String makeUse() {
		// TODO Auto-generated method stub
		return "hello world";
	}





}