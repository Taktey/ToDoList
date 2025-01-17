package com.example.todolist.exception;

public class AlreadyDeletedException extends RuntimeException {
    private static final String FILE_ALREADY_DELETED_MSG = "File already deleted";

    public AlreadyDeletedException() {
        super(FILE_ALREADY_DELETED_MSG);
    }

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
