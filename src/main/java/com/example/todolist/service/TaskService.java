package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TaskEntity;
import com.example.todolist.models.UserEntity;
import com.example.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService extends BaseService{
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public TaskDto getTaskById(Long taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        return new TaskDto(
                task.getStartDate(),
                task.getEndDate(),
                task.getDescription(),
                task.getTags(),
                task.getUser().getId());
    }

    public void assignTask(Long taskId, Long userId) throws NoSuchTaskFoundException, NoSuchUserFoundException {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        UserEntity userEntity = userService.getUserById(userId);
        task.setUser(userEntity);
        taskRepository.save(task);
    }

    public Long createTask(TaskDto taskDto) throws NoSuchUserFoundException{
        UserEntity user = userService.getUserById(taskDto.getUserId());
        TaskEntity taskEntity = new TaskEntity(
                taskDto.getStartDate(),
                taskDto.getEndDate(),
                taskDto.getDescription(),
                taskDto.getTags(),user);
        return taskRepository.save(taskEntity).getId();
    }

    public void updateTask(Long taskId, TaskDto taskDto) throws NoSuchTaskFoundException {
        TaskEntity toBeUpdate = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        if (taskDto.getDescription() != null) {
            toBeUpdate.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStartDate() != null) {
            toBeUpdate.setStartDate(taskDto.getStartDate());
        }
        if (taskDto.getEndDate() != null) {
            toBeUpdate.setEndDate(taskDto.getEndDate());
        }
        if (taskDto.getTags() != null) {
            toBeUpdate.setTags(taskDto.getTags());
        }
    }

    public void deleteTask(Long taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        task.setIsRemoved(true);
        taskRepository.save(task);
    }

    public Set<String> addTags(Long taskId, List<String> tags) {
        TaskEntity taskToAddTags = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(()->new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        Set<String> tagsAfterAdding = taskToAddTags.getTags();
        tagsAfterAdding.addAll(tags);
        taskToAddTags.setTags(tagsAfterAdding);
        taskRepository.save(taskToAddTags);
        return tagsAfterAdding;
    }

    public List<TaskDto> getTasksByTags(List<String> tags) {
        return null; // Доделать
    }
}
