package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.FileEntity;
import com.example.todolist.models.TaskEntity;

import java.util.List;

public class TaskMapper {
    public static TaskDto taskEntityToDto(TaskEntity taskEntity){
        List<Long> files;
        try {
            files = taskEntity.getFiles().stream().map(FileEntity::getId).toList();
        } catch (NullPointerException e){
            files = List.of(0L);
        }
        return new TaskDto(taskEntity.getId(),
                taskEntity.getStartDate(),
                taskEntity.getEndDate(),
                taskEntity.getDescription(),
                taskEntity.getUser().getId(),
                files);
    }
}
