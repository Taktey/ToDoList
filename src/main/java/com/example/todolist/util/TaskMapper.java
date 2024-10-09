package com.example.todolist.util;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.Task;

public class TaskMapper {
    public static Task taskDtoToEntity(TaskDto taskDto) {
        return new Task(
                null,
                taskDto.getStartDate(),
                taskDto.getEndDate(),
                taskDto.getDescription(),
                taskDto.getTags(),
                false,
                taskDto.getExecutorId());
    }

    public static TaskDto taskEntityToDto(Task task) {
        return new TaskDto(
                task.getStartDate(),
                task.getEndDate(),
                task.getDescription(),
                task.getTags(),
                task.getExecutorId());
    }
}
