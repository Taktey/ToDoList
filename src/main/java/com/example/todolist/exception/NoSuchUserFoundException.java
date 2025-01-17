package com.example.todolist.exception;

public class NoSuchUserFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_MSG = "User not found";

    public NoSuchUserFoundException() {
        super(USER_NOT_FOUND_MSG);
    }

    public NoSuchUserFoundException(String message) {
        super(message);
    }
}
