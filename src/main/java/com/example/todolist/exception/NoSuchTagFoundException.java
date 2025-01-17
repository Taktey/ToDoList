package com.example.todolist.exception;

public class NoSuchTagFoundException extends RuntimeException {
    private static final String TAG_NOT_FOUND_MSG = "Tag not found";

    public NoSuchTagFoundException() {
        super(TAG_NOT_FOUND_MSG);
    }

    public NoSuchTagFoundException(String message) {
        super(message);
    }
}
