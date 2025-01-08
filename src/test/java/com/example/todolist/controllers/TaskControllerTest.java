package com.example.todolist.controllers;

import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.TaskCreateDto;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void testGetTask_Success() throws Exception {
        TaskDto taskDto = new TaskDto(1L, null, null, "тестовое описание",1L,null);
        when(taskService.getTaskById(1L)).thenReturn(taskDto);
        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("тестовое описание"));
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testGetTask_NotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenThrow(new NoSuchTaskFoundException("Задача не найдена"));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Задача не найдена"));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testAssignTask_Success() throws Exception {
        mockMvc.perform(post("/task/1/to/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id = 2 reassigned to user id = 2"));

        verify(taskService, times(1)).assignTask(1L, 2L);
    }

    @Test
    void testAssignTask_TaskNotFound() throws Exception {
        doThrow(new NoSuchTaskFoundException("Задача не найдена")).when(taskService).assignTask(1L, 2L);
        mockMvc.perform(post("/task/1/to/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Задача не найдена"));
        verify(taskService, times(1)).assignTask(1L, 2L);
    }

    @Test
    void testAssignTask_UserNotFound() throws Exception {
        doThrow(new NoSuchUserFoundException("Пользователь не найден")).when(taskService).assignTask(1L, 2L);
        mockMvc.perform(post("/task/1/to/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Пользователь не найден"));
        verify(taskService, times(1)).assignTask(1L, 2L);
    }

    @Test
    void testCreateTask_Success() throws Exception {
        when(taskService.createTask(any(TaskCreateDto.class))).thenReturn(1L);
        mockMvc.perform(post("/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Тестовое описание\",\"userId\":2}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Task successfully created, id = 1")));
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        mockMvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"description\":\"Тестовое описание\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Task with id = 1 successfully updated.")));
        verify(taskService, times(1)).updateTask(any(TaskDto.class));
    }

    @Test
    void testDeleteTask_Success() throws Exception {
        mockMvc.perform(delete("/task/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id = 1 successfully removed."));
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testDeleteTask_TaskNotFound() throws Exception {
        doThrow(new NoSuchTaskFoundException("Задача не найдена")).when(taskService).deleteTask(1L);
        mockMvc.perform(delete("/task/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Задача не найдена"));
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testRestoreTask_Success() throws Exception {
        mockMvc.perform(put("/task/restore/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id = 2 successfully restored."));
        verify(taskService, times(1)).restoreTask(1L);
    }

    @Test
    void testRestoreTask_TaskNotFound() throws Exception {
        doThrow(new NoSuchTaskFoundException("Задача не найдена")).when(taskService).restoreTask(1L);
        mockMvc.perform(post("/task/restore/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Задача не найдена"));
        verify(taskService, times(1)).deleteTask(1L);
    }
}