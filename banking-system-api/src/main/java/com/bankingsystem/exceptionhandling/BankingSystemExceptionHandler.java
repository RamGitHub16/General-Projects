package com.bankingsystem.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankingSystemExceptionHandler {

	@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
	public ResponseEntity<String> handleException(org.springframework.dao.DataIntegrityViolationException e) {
		return new ResponseEntity<String>("Please check the Account Number, Account Number already exists", HttpStatus.BAD_REQUEST);

	}

}
