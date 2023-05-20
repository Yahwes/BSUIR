package com.universe.labs.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class YearException extends RuntimeException {
    private HttpStatus statusCode;
    private String reason;

    public YearException(HttpStatus statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }
}