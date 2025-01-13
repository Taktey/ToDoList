package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.TaskCreateDto;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TaskEntity;
import com.example.todolist.models.UserEntity;
import com.example.todolist.repositories.TaskRepository;
import com.example.todolist.util.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends BaseService {
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
        return TaskMapper.taskEntityToDto(task);
    }

    public void assignTask(Long taskId, Long userId) throws NoSuchTaskFoundException, NoSuchUserFoundException {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        UserEntity userEntity = userService.getUserById(userId);
        task.setUser(userEntity);
        taskRepository.save(task);
    }

    public Long createTask(TaskCreateDto taskCreateDto) throws NoSuchUserFoundException {

        UserEntity user = userService.getUserById(taskCreateDto.getUserId());

        TaskEntity taskEntity = new TaskEntity(
                taskCreateDto.getStartDate(),
                taskCreateDto.getEndDate(),
                taskCreateDto.getDescription(),
                user);
        return taskRepository.save(taskEntity).getId();
    }

    public void updateTask(TaskDto taskDto) throws NoSuchTaskFoundException {
        TaskEntity toBeUpdate = taskRepository.findByIdAndIsRemovedIsFalse(taskDto.getId())
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
        taskRepository.save(toBeUpdate);
    }

    public void deleteTask(Long taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        task.setIsRemoved(true);
        taskRepository.save(task);
    }

    public void restoreTask(Long taskId) {
        TaskEntity task = taskRepository.findByIdAndIsRemovedIsTrue(taskId)
                .orElseThrow(() -> new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        task.setIsRemoved(false);
        taskRepository.save(task);
    }
}
