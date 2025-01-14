package com.example.todolist.service;

import lombok.Getter;

@Getter
public class BaseService {
    private final String fileNotFoundMsg = "File not found";
    private final String fileAlreadyDeletedMsg = "File already deleted";
    private final String taskNotFoundMsg = "Задача с таким id не найдена";
    private final String userNotFoundMsg = "Пользователь не найден";
    private final String tagNotFoundMsg = "Тег не найден: ";

}
