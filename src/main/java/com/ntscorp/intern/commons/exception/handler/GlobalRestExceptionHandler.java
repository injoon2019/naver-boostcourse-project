package com.ntscorp.intern.commons.exception.handler;


import com.ntscorp.intern.commons.exception.customexception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalRestExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> notFoundExceptionHandler(EmptyResultDataAccessException e) {
		LOGGER.error("error log = {}", e.toString());
		return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<String> notValidExceptionHandler(InvalidInputException e) {
		LOGGER.error("error log = {}", e.toString());
		return new ResponseEntity<>("Not Valid", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> unknownExceptionHandler(Exception e) {
		LOGGER.error("unknown error log = {}", e.toString());
		return new ResponseEntity<>("Unknown Exception", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
