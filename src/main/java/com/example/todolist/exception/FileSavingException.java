package com.example.todolist.exception;

import java.io.IOException;

public class FileSavingException extends IOException {
    private static final String FAILED_TO_SAVE_FILE_MSG = "Failed to save file";

    public FileSavingException() {
        super(FAILED_TO_SAVE_FILE_MSG);
    }

    public FileSavingException(String message) {
        super(message);
    }
}
