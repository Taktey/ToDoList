package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TaskEntity;

public class TaskMapper {
    public static TaskDto taskEntityToDto(TaskEntity taskEntity){
        return new TaskDto(taskEntity.getId(),
                taskEntity.getStartDate(),
                taskEntity.getEndDate(),
                taskEntity.getDescription(),
                taskEntity.getUser().getId());
    }
}
