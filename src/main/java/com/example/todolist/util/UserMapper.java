package com.example.todolist.util;

import com.example.todolist.dto.UserDto;
import com.example.todolist.models.User;

import java.time.LocalDate;

public class UserMapper {
    public static User userDtoToEntity(String userName) {
        return new User(
                null,
                userName,
                LocalDate.now(),
                false,
                null);
    }

    public static UserDto userEntityToDto(User user) {
        return null;
    }
}
