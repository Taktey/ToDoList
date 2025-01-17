package com.example.todolist.exception;

public class IllegalMethodUsageException extends RuntimeException {
    private static final String ILLEGAL_METHOD_MSG = "Incorrect method usage.\n";

    public IllegalMethodUsageException() {
        super(ILLEGAL_METHOD_MSG);
    }

    public IllegalMethodUsageException(String message) {
        super(ILLEGAL_METHOD_MSG + message);
    }
}
