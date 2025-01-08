package com.example.todolist.Exceptions;

public class IllegalMethodUsageException extends RuntimeException {
    public IllegalMethodUsageException(String message) {
        super(message);
    }
}
