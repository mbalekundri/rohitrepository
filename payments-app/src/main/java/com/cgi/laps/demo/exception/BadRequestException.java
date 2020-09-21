package com.cgi.laps.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException.java: A Custom class to handle to handle BadRequest
 * exceptions during create update accounts operation calls from service/DAO
 * class
 * 
 * @author balekundrim
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable t) {
		super(message, t);
	}

}
