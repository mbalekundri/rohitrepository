package com.cgi.laps.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.cgi.laps.api.model.TransactionResult;

/**
 * ServerResponse.java: To show status and detailed message after create/update
 * account
 * 
 * @author balekundrim
 *
 */
public class ServerResponse {

	// General operation success or failure status
	private TransactionResult result;
	// General error message about nature of error
	private String message;
	// Specific errors in API request processing
	private List<String> details;

	public ServerResponse() {
		super();
	}

	public ServerResponse(TransactionResult result, String message, List<String> details) {
		super();
		this.result = result;
		this.message = message;
		this.details = details;
	}

	public TransactionResult getResult() {
		return result;
	}

	public void setResult(TransactionResult result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		if (details == null) {
			details = new ArrayList<>();
		}
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

}
