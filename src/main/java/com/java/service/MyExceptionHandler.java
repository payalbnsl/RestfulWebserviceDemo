 package com.java.service;

import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.dto.Error;

@RestControllerAdvice(basePackages = "com.java.dao")
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	 * @ExceptionHandler(DataAccessException.class)
	 * 
	 * @ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR, reason="Server error")
	 * public void handleError() {
	 * 
	 * }
	 */

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Error> handleError(Exception e, WebRequest req) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(500,
				"Error while executing " + req.getDescription(false) + e.getMessage(), LocalDateTime.now()));
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Error response = new Error(500, "Http Method not supported", LocalDateTime.now());
		return  handleExceptionInternal(
			      ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	

}
