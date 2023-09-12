package com.example.book.store.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "books-store")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    private String description;

    
    private Category category;
    
    
    
    @Min(value = 0, message = "Price should be positive value.")
    private float price;

    /**
     * Amount of book available
     */
    @Min(value = 0, message = "Total Count should be positive value.")
    private int totalCount;

    

}