package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    LocalDate startDate;
    LocalDate endDate;
    String description;
    Set<String> tags;
    Long userId;
}

/*{
        "startDate":"2024-11-01",
        "endDate":"2024-11-15",
        "description":"khvhvhkghgkg",
        "tags":["tag1","tag2","tag3"],
        "userId":12345
        }*/
