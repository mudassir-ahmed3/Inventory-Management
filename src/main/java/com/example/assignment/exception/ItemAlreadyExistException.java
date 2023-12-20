package com.example.assignment.exception;

public class ItemAlreadyExistException extends AppException{
    public ItemAlreadyExistException(String message) {
        super(message);
    }

    public ItemAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
