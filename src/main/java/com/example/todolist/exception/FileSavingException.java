package com.example.todolist.exception;

public class FileSavingException extends RuntimeException {
    private static final String FAILED_TO_SAVE_FILE_MSG = "Failed to save file!";

    public FileSavingException() {
        super(FAILED_TO_SAVE_FILE_MSG);
    }

    public FileSavingException(String message) {
        super(FAILED_TO_SAVE_FILE_MSG + "\n" + message);
    }
}