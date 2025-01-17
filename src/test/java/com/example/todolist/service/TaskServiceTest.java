package com.example.todolist.service;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.exception.NoSuchTaskFoundException;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_Success() throws NoSuchTaskFoundException {
        Long taskId = 1L;
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setDescription("Test Task");
        UserEntity user = new UserEntity();
        user.setId(2L);
        task.setUser(user);

        when(taskRepository.findByIdAndRemovedIsFalse(taskId)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Test Task", result.getDescription());
        assertEquals(2L, result.getUserId());
        verify(taskRepository, times(1)).findByIdAndRemovedIsFalse(taskId);
    }

    @Test
    void getTaskById_TaskNotFound() {
        Long taskId = 1L;
        when(taskRepository.findByIdAndRemovedIsFalse(taskId)).thenReturn(Optional.empty());

        NoSuchTaskFoundException exception = assertThrows(NoSuchTaskFoundException.class, () -> {
            taskService.getTaskById(taskId);
        });
        assertEquals(new NoSuchTaskFoundException().getMessage(), exception.getMessage());
        verify(taskRepository, times(1)).findByIdAndRemovedIsFalse(taskId);
    }

    /*@Test
    void assignTask_Success() throws NoSuchTaskFoundException, NoSuchUserFoundException {
        Long taskId = 1L;
        Long userId = 2L;

        TaskEntity task = new TaskEntity();
        UserEntity user = new UserEntity();

        when(taskRepository.findByIdAndRemovedIsFalse(taskId)).thenReturn(Optional.of(task));
        when(userService.getUserById(userId)).thenReturn(user);

        taskService.assignTask(taskId, userId);

        assertEquals(user, task.getUser());
        verify(taskRepository, times(1)).findByIdAndRemovedIsFalse(taskId);
        verify(userService, times(1)).getUserById(userId);
        verify(taskRepository, times(1)).save(task);
    }*/

    /*@Test
    void createTask_Success() throws NoSuchUserFoundException {
        TaskCreateDto taskCreateDto = new TaskCreateDto( null, null, "New Task", 2L);
        UserEntity user = new UserEntity();
        user.setId(2L);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);

        when(userService.getUserById(2L)).thenReturn(user);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskDto taskDto = taskService.createTask(taskCreateDto);

        assertEquals(1L, taskId);
        verify(userService, times(1)).getUserById(2L);
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }*/

    /*@Test
    void updateTask_Success() throws NoSuchTaskFoundException {
        TaskDto taskDto = new TaskDto(1L, null, null, "Updated Task", null,null);

        TaskEntity task = new TaskEntity();
        when(taskRepository.findByIdAndIsRemovedIsFalse(taskDto.getId())).thenReturn(Optional.of(task));

        taskService.updateTask(taskDto);

        assertEquals("Updated Task", task.getDescription());
        verify(taskRepository, times(1)).findByIdAndIsRemovedIsFalse(taskDto.getId());
    }*/

    @Test
    void deleteTask_Success() throws NoSuchTaskFoundException {
        Long taskId = 1L;
        TaskEntity task = new TaskEntity();
        task.setRemoved(false);

        when(taskRepository.findByIdAndRemovedIsFalse(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        assertTrue(task.getRemoved());
        verify(taskRepository, times(1)).findByIdAndRemovedIsFalse(taskId);
        verify(taskRepository, times(1)).save(task);
    }
}
