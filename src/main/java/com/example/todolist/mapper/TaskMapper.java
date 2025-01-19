package com.example.todolist.mapper;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.model.FileEntity;
import com.example.todolist.model.TagEntity;
import com.example.todolist.model.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public final class TaskMapper {

    private static TaskMapper taskMapper;

    public static TaskMapper getInstance() {
        synchronized (TaskMapper.class) {
            if (taskMapper == null) {
                taskMapper = new TaskMapper();
            }
        }
        return taskMapper;
    }

    public final TaskDTO taskEntityToDto(TaskEntity taskEntity) {
        List<UUID> files;
        if (taskEntity.getFiles() == null) {
            files = new ArrayList<>();
        } else {
            files = taskEntity.getFiles().stream().map(FileEntity::getId).toList();
        }

        Set<String> tags = taskEntity.getTags().stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
        return TaskDTO.builder()
                .id(taskEntity.getId())
                .startDate(taskEntity.getStartDate())
                .endDate(taskEntity.getEndDate())
                .description(taskEntity.getDescription())
                .userId(taskEntity.getUser().getId())
                .fileIds(files)
                .tags(tags)
                .build();
    }

    private TaskMapper() {
    }
}
