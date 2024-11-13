package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TaskEntity;

public class TaskMapper {
    public static TaskEntity taskDtoToEntity(TaskDto taskDto) {
        return new TaskEntity(
                null,
                taskDto.getStartDate(),
                taskDto.getEndDate(),
                taskDto.getDescription(),
                taskDto.getTags(),
                false,
                taskDto.getExecutorId());
    }

    public static TaskDto taskEntityToDto(TaskEntity task) {
        return new TaskDto(
                task.getStartDate(),
                task.getEndDate(),
                task.getDescription(),
                task.getTags(),
                task.getExecutorId());
    }
}
