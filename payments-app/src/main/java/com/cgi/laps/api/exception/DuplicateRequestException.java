package com.cgi.laps.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DuplicateRequestException.java: An exception class to handle conflicts of
 * information/duplicate records.
 * 
 * @author balekundrim
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateRequestException(String message) {
		super(message);
	}

	public DuplicateRequestException(String message, Throwable t) {
		super(message, t);
	}

}
