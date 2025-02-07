package com.example.todolist.exception;

public class TagAlreadyExistsException extends RuntimeException{
    private static final String TAG_ALREADY_EXISTS_MSG = "Тег уже существует!";
    public TagAlreadyExistsException(){super(TAG_ALREADY_EXISTS_MSG);}
}
