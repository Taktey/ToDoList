package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.exception.IllegalMethodUsageException;
import com.example.todolist.exception.NoSuchTagFoundException;
import com.example.todolist.exception.NoSuchTaskFoundException;
import com.example.todolist.exception.NoSuchUserFoundException;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.model.TagEntity;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.TagRepository;
import com.example.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TagService tagService;
    private final TagRepository tagRepository;

    public TaskDTO getTaskById(Long taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        return TaskMapper.getInstance().taskEntityToDto(task);
    }

    public void assignTask(Long taskId, Long userId) throws NoSuchTaskFoundException, NoSuchUserFoundException {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        UserEntity userEntity = UserMapper.userDtoToEntity(userService.getUserById(userId));
        task.setUser(userEntity);
        taskRepository.save(task);
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws NoSuchUserFoundException {
        UserEntity user = UserMapper.userDtoToEntity(userService.getUserById(taskDTO.getUserId()));

        Set<TagEntity> tags = tagService.getOrCreateTags(taskDTO.getTags());

        TaskEntity taskEntity = new TaskEntity(
                taskDTO.getStartDate(),
                taskDTO.getEndDate(),
                taskDTO.getDescription(),
                user, tags);
        return TaskMapper.getInstance()
                .taskEntityToDto(taskRepository.save(taskEntity));
    }

    public TaskDTO updateTask(TaskDTO taskDto) throws NoSuchTaskFoundException {
        if (taskDto.getUserId() != null) {
            throw new IllegalMethodUsageException("To assign task to user use specified method");
        }
        TaskEntity toBeUpdate = taskRepository.findByIdAndRemovedIsFalse(taskDto.getId())
                .orElseThrow(NoSuchTaskFoundException::new);
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
        return TaskMapper.getInstance()
                .taskEntityToDto(toBeUpdate);
    }

    public void deleteTask(Long taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        task.setRemoved(true);
        taskRepository.save(task);
    }

    public void restoreTask(Long taskId) {
        TaskEntity task = taskRepository.findByIdAndRemovedIsTrue(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        task.setRemoved(false);
        taskRepository.save(task);
    }

    public TaskDTO assignTagToTask(String tagName, Long taskId) {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        TagEntity tag = tagRepository.findByName(tagName)
                .orElseThrow(NoSuchTagFoundException::new);
        if (!task.getTags().contains(tag)) {
            task.getTags().add(tag);
            tag.getTasks().add(task);
        }
        return TaskMapper.getInstance()
                .taskEntityToDto(taskRepository.save(task));

    }
}
