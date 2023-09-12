package com.example.book.store.service;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book.store.entity.Book;
import com.example.book.store.entity.BookDto;
import com.example.book.store.entity.Category;
import com.example.book.store.entity.dao.BookRepository;
import com.example.book.store.exceptions.BookNotFoundException;
import com.example.book.store.exceptions.DuplicateResourceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookStoreServiceImpl implements BookService {
	
    private static final Logger log = LoggerFactory.getLogger(BookStoreServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;
    private  ModelMapper modelMapper = new ModelMapper();


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
      Book updatedBook = bookRepository.save(existingBook);
      return updatedBook;
    }

    @Override
    public void deleteBook(Long bookId) {
       bookRepository.deleteById(bookId);
    }


	


    @Override
    public int getNumberOfBooksById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        //If book is present get Total Count else return 0
        return book.isPresent() ? book.get().getTotalCount() : 0;
    }
    
    
    @Override
	public List<Book> getBookByCategoryKeyWord(
                                                  Category category) {

        //if the status is Available, gives list of books which are available
    	log.info("Fetch all the books by category .");
        List<Book> book = bookRepository.findAllBookByCategoryAndKeyword(category.getValue());
        return book ;
//        		mapBookListToBooDtoList(book);
    }
    
    
//    //Convert List of books to List of bookDto
//    private List<BookDto> mapBookListToBooDtoList(List<Book> books) {
//        return books.stream()
//                .map(book -> modelMapper.map(book, BookDto.class))
//                .collect(Collectors.toList());
//    }
    
    
    
    @Override
//    @Transactional
    public void addNewBook(BookDto bookDto) {
        //Check if bookDto is previously present
        Optional<Book> bookById = bookRepository.findById(bookDto.getId());
        bookById.ifPresent(book -> {
            throw new DuplicateResourceException("Book with same id present. " +
                    "Either use update methods to update the book counts or use addBook(Long id, int quantityToAdd) methods");
        });
        if (!bookById.isPresent()) {
            log.info("No Duplicates found.");
            //Map bookDto to book
            Book book = modelMapper.map(bookDto, Book.class);
            //Set the status to available
            log.info("The data are mapped and ready to save.");

            //Save to book
            bookRepository.save(book);
        }
        
    }
     

	@Override
	public void addBook(Long id, int quantityToAdd) {
		 Book book = bookRepository.findById(id)
                 .orElseThrow(() -> new BookNotFoundException("Book with id:" + id + " is not registered. Use addNewBook to register."));
         log.info("The book with id " + id + " is registered");

         int totalCountAfterAdd = book.getTotalCount() + quantityToAdd;
         book.setTotalCount(totalCountAfterAdd);

         bookRepository.save(book);		
	}
}