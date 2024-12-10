package com.issue_tracker.issue_tracker.exception;

public class ForbiddenException extends Exception {
    
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ForbiddenException(Throwable cause) {
        super(cause);
    }
}
