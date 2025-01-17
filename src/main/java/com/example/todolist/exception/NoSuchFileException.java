package com.example.todolist.exception;

public class NoSuchFileException extends RuntimeException {
    private static final String FILE_NOT_FOUND_MSG = "File not found";

    public NoSuchFileException() {
        super(FILE_NOT_FOUND_MSG);
    }

    public NoSuchFileException(String message) {
        super(message);
    }
}
