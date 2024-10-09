package com.example.todolist.Exceptions;

public class NoSuchTaskFoundException extends RuntimeException{
    public NoSuchTaskFoundException(String message){
        super(message);
    }
}
