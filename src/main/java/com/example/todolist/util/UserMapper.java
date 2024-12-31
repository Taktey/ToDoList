package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.models.UserEntity;

import java.time.LocalDate;
import java.util.List;

public class UserMapper {
    public static UserEntity userDtoToEntity(String userName) {
        return new UserEntity(
                null,
                userName,
                LocalDate.now(),
                false,
                null);
    }

    public static UserDto userEntityToDto(UserEntity user) {
        List<TaskDto> tasks = user.getTasks()
                .stream().map(TaskMapper::taskEntityToDto)
                .toList();
        return new UserDto(user.getName(),tasks);
    }
}
