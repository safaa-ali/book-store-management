package com.example.book.store.entity.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.store.entity.Book;
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findBookByGenre(String genre);
}