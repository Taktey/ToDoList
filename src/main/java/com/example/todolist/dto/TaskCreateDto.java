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
public class TaskCreateDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long userId;
    private Set<String> tags;
}
