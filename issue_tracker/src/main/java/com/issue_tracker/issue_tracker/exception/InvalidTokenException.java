package com.issue_tracker.issue_tracker.exception;

public class InvalidTokenException extends Exception {
    
    public InvalidTokenException() {
        super("Token invalido");
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}
