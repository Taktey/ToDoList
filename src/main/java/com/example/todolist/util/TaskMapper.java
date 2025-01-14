package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.FileEntity;
import com.example.todolist.models.TagEntity;
import com.example.todolist.models.TaskEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskDto taskEntityToDto(TaskEntity taskEntity){
        List<Long> files;
        try {
            files = taskEntity.getFiles().stream().map(FileEntity::getId).toList();
        } catch (NullPointerException e){
            files = List.of(0L);
        }
        Set<String> tags = taskEntity.getTags().stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
        return new TaskDto(taskEntity.getId(),
                taskEntity.getStartDate(),
                taskEntity.getEndDate(),
                taskEntity.getDescription(),
                taskEntity.getUser().getId(),
                files,
                tags);
    }
}
