package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.models.Task;
import com.example.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final String taskNotFoundErrorMessage = "Задача с таким id не найдена";

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long taskId) throws NoSuchTaskFoundException {
        return taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(taskNotFoundErrorMessage));
    }

    public void reAssignTask(Long taskId, Long userId) throws NoSuchTaskFoundException {
        Task task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(taskNotFoundErrorMessage));
        task.setExecutorId(userId);
        taskRepository.save(task);
    }

    public Long createTask(Task task) {
        return taskRepository.save(task).getId();
    }

    public void updateTask(Long taskId, Task task) throws NoSuchTaskFoundException {
        Task toBeUpdate = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(taskNotFoundErrorMessage));
        if (task.getDescription() != null) {
            toBeUpdate.setDescription(task.getDescription());
        }
        if (task.getStartDate() != null) {
            toBeUpdate.setStartDate(task.getStartDate());
        }
        if (task.getEndDate() != null) {
            toBeUpdate.setEndDate(task.getEndDate());
        }
        if (task.getTags() != null) {
            toBeUpdate.setTags(task.getTags());
        }
    }

    public void deleteTask(Long taskId) throws NoSuchTaskFoundException {
        Task task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(taskNotFoundErrorMessage));
        task.setIsRemoved(true);
        taskRepository.save(task);
    }
}
