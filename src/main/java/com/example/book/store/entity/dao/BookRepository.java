package com.example.book.store.entity.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.book.store.entity.Book;
public interface BookRepository extends JpaRepository<Book, Long> {

	
//	List<Book> findAllByCategory(String categoryName);
	
	
	 @Query(value = "Select * from book b where " +
	            "b.category=?",
	            nativeQuery = true)
	    List<Book> findAllBookByCategoryAndKeyword(int category);

	    @Query(value = "Select IF(SUM(b.sold) IS NULL,0,SUM(b.sold)) from book b where " +
	            "(b.title like %?1% OR CAST(b.id as CHAR) like %?1% OR LOWER(b.author) like %?1%) " +
	            "AND b.category=?2 AND b.sold>0",
	            nativeQuery = true)
	    long countNumberOfBooksSold(String keyword, int category);

}