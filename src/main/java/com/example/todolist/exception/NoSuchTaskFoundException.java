package com.example.todolist.exception;

public class NoSuchTaskFoundException extends RuntimeException {
    private static final String TASK_NOT_FOUND_MSG = "Task with provided id not found";

    public NoSuchTaskFoundException() {
        super(TASK_NOT_FOUND_MSG);
    }

    public NoSuchTaskFoundException(String message) {
        super(message);
    }
}
