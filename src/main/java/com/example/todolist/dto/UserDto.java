package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    Long id;
    String name;
    List<TaskDto> tasks;
}
