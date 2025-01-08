package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.dto.UserCreateDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.models.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserEntity userDtoToEntity(UserCreateDto user) {
        return new UserEntity(
                null,
                user.getUserName(),
                LocalDate.now(),
                false,
                new ArrayList<>());
    }

    public static UserDto userEntityToDto(UserEntity user) {
        if(user.getTasks()==null) return new UserDto(user.getId(), user.getName(),new ArrayList<>());
        List<TaskDto> tasks = user.getTasks()
                .stream().map(TaskMapper::taskEntityToDto)
                .toList();
        return new UserDto(user.getId(), user.getName(),tasks);
    }
}
