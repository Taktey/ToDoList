package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;
    private String fileName;
    private Long taskId;

    public FileDto(String fileName, Long taskId) {
        this.fileName = fileName;
        this.taskId = taskId;
    }
}
