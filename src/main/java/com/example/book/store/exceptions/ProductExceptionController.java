package com.example.book.store.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.book.store.exceptions.entity.ApplicationError;

@ControllerAdvice
public class ProductExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductNotfoundException.class)
	public ResponseEntity<ApplicationError> handleCustomerNotFoundException(ProductNotfoundException exception,
			WebRequest webRequest) {
		ApplicationError error = new ApplicationError();
		error.setCode(404);
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}