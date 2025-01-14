package com.example.todolist.Exceptions;

public class NoSuchTagFoundException extends RuntimeException{
    public NoSuchTagFoundException(String message){
        super(message);
    }
}
