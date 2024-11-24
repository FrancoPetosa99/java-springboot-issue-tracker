package com.issue_tracker.issue_tracker.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    // Constructor with message only
    public CustomException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    // Constructor with HttpStatus and message
    public CustomException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    // Getters
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}