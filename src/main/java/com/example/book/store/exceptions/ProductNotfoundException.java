package com.example.book.store.exceptions;



import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotfoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotfoundException(String message) {
		super(message);
	}

}