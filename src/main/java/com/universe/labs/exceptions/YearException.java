package com.universe.labs.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class YearException extends ResponseStatusException{

	public YearException(HttpStatusCode status, String reason) {
        super(status, reason);
	}
}