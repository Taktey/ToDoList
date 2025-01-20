package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.dto.TasksToUserAssignDTO;
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
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final UserMapper userMapper = UserMapper.getInstance();
    private final TaskMapper taskMapper = TaskMapper.getInstance();

    public TaskDTO getTaskById(UUID taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        return TaskMapper.getInstance().taskEntityToDto(task);
    }

    public void assignTasks(TasksToUserAssignDTO dto) throws NoSuchTaskFoundException, NoSuchUserFoundException {
        Set<UUID> taskIds = dto.getTaskIds();
        UserEntity user = userMapper.userDtoToEntity(userService.getUserById(dto.getUserId()));
        taskIds.stream()
                .map(id -> taskRepository.findByIdAndRemovedIsFalse(id)
                        .orElseThrow(() -> new NoSuchTaskFoundException("task with id: " + id + " not found!")))
                .peek(task -> task.setUser(user))
                .peek(task -> user.getTasks().add(task))
                .peek(taskRepository::save);
        userService.saveChanges(user);

        /*task.setUser(userEntity);
        taskRepository.save(task);*/
    }

    public TaskDTO createTask(TaskDTO taskDTO) throws NoSuchUserFoundException {
        UserEntity user = userMapper.userDtoToEntity(userService.getUserById(taskDTO.getUserId()));
        Set<TagEntity> tags = tagService.getOrCreateTags(taskDTO.getTags());
        TaskEntity taskEntity = new TaskEntity(
                taskDTO.getStartDate(),
                taskDTO.getEndDate(),
                taskDTO.getDescription(),
                user, tags);
        userService.saveChanges(user);
        return TaskMapper.getInstance()
                .taskEntityToDto(taskRepository.save(taskEntity));
    }

    public TaskDTO updateTask(TaskDTO taskDTO) throws NoSuchTaskFoundException {
        if (taskDTO.getUserId() != null) {
            throw new IllegalMethodUsageException("To assign task to user use specified method");
        }
        TaskEntity toBeUpdate = taskRepository.findByIdAndRemovedIsFalse(taskDTO.getId())
                .orElseThrow(NoSuchTaskFoundException::new);
        if (taskDTO.getDescription() != null) {
            toBeUpdate.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStartDate() != null) {
            toBeUpdate.setStartDate(taskDTO.getStartDate());
        }
        if (taskDTO.getEndDate() != null) {
            toBeUpdate.setEndDate(taskDTO.getEndDate());
        }
        if (taskDTO.getTags() != null) {
            Set<TagEntity> tags = tagService.getOrCreateTags(taskDTO.getTags());
            for (TagEntity tag : tags) {
                toBeUpdate.getTags().add(tag);
            }
        }
        taskRepository.save(toBeUpdate);
        return TaskMapper.getInstance()
                .taskEntityToDto(toBeUpdate);
    }

    public void deleteTask(UUID taskId) throws NoSuchTaskFoundException {
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        task.setRemoved(true);
        taskRepository.save(task);
    }

    public void restoreTask(UUID taskId) {
        TaskEntity task = taskRepository.findByIdAndRemovedIsTrue(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        task.setRemoved(false);
        taskRepository.save(task);
    }

    public TaskDTO assignTagToTask(String tagName, UUID taskId) {
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

    public Set<TaskDTO> tasksByUserId(UUID id) {
        return userService.tasksByUserId(id);
    }

    public TaskDTO assignTagsToTask(Set<String> tagNames, UUID taskId) {
        Set<TagEntity> tagEntities = tagService.getOrCreateTags(tagNames);
        TaskEntity task = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        for (TagEntity tagEntity : tagEntities) {
            task.getTags().add(tagEntity);
        }
        tagEntities.stream()
                .peek(tag -> tag.getTasks().add(task))
                .forEach(tagRepository::save);
        return taskMapper.taskEntityToDto(taskRepository.save(task));
    }
}
