package com.example.book.store.entity.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.book.store.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value = "Select * from book b where " + "b.category=?", nativeQuery = true)
	List<Book> findAllBookByCategoryAndKeyword(int category);

}