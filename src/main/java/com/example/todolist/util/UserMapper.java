package com.example.todolist.util;

import com.example.todolist.dto.UserDto;
import com.example.todolist.models.UserEntity;

import java.time.LocalDate;

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
        return null;
    }
}
