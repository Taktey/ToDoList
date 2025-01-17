package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private UUID id;
    private String fileName;
    private UUID taskId;

    public FileDTO(String fileName, UUID taskId) {
        this.fileName = fileName;
        this.taskId = taskId;
    }
}
