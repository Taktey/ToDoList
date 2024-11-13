package com.example.todolist.Exceptions;

public class NoSuchUserFoundException extends RuntimeException{
    public NoSuchUserFoundException(String message){super(message);}
}
