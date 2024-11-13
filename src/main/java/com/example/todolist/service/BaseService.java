package com.example.todolist.service;

public class BaseService {
    private final String fileNotFoundMsg = "File not found";
    private final String fileAlreadyDeletedMsg = "File already deleted";
    private final String taskNotFoundMsg = "Задача с таким id не найдена";
    private final String userNotFoundMsg = "Пользователь не найден";

    public String getUserNotFoundMsg() {
        return userNotFoundMsg;
    }

    public String getFileNotFoundMsg() {
        return fileNotFoundMsg;
    }

    public String getFileAlreadyDeletedMsg() {
        return fileAlreadyDeletedMsg;
    }

    public String getTaskNotFoundMsg() {
        return taskNotFoundMsg;
    }
}
