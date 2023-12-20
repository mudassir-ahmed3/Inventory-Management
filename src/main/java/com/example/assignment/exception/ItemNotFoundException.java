package com.example.assignment.exception;

public class ItemNotFoundException extends AppException{
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
