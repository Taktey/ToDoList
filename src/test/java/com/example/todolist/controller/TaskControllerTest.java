package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.dto.TasksToUserAssignDTO;
import com.example.todolist.service.TagService;
import com.example.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private UUID taskId;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        taskId = UUID.randomUUID();
        taskDTO = TaskDTO.builder()
                .id(taskId)
                .description("Test Task")
                .userId(UUID.randomUUID())
                .tags(Set.of()).build();
    }

    @Test
    @DisplayName("Получение задачи по ID")
    void getTaskById() throws Exception {
        when(taskService.getTaskById(taskId)).thenReturn(taskDTO);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.description").value("Test Task"));

        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    @DisplayName("Создание задачи")
    void createTask() throws Exception {
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Test Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Task"));

        verify(taskService, times(1)).createTask(any(TaskDTO.class));
    }

    @Test
    @DisplayName("Обновление задачи")
    void updateTask() throws Exception {
        when(taskService.updateTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + taskId + "\", \"description\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Task"));

        verify(taskService, times(1)).updateTask(any(TaskDTO.class));
    }

    @Test
    @DisplayName("Удаление задачи")
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/delete/" + taskId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    @DisplayName("Восстановление задачи")
    void restoreTask() throws Exception {
        doNothing().when(taskService).restoreTask(taskId);

        mockMvc.perform(put("/tasks/" + taskId + "/restore"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).restoreTask(taskId);
    }

    @Test
    @DisplayName("Назначение задачи пользователю")
    void assignTasksToUser() throws Exception {
        doNothing().when(taskService).assignTasks(any(TasksToUserAssignDTO.class));

        mockMvc.perform(put("/tasks/assign/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskIds\": [\"" + taskId + "\"], \"userId\": \"" + UUID.randomUUID() + "\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).assignTasks(any(TasksToUserAssignDTO.class));
    }

    @Test
    @DisplayName("Назначение тега задаче")
    void assignTagsToTask() throws Exception {
        when(taskService.assignTagsToTask(anySet(), any(UUID.class))).thenReturn(taskDTO);

        mockMvc.perform(put("/tasks/assign/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagNames\": [\"tag1\", \"tag2\"], \"taskId\": \"" + taskId + "\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).assignTagsToTask(anySet(), any(UUID.class));
    }

    @Test
    @DisplayName("Получение задачь по тегам")
    void getTasksByTags() throws Exception {
        when(tagService.getTasksHaveTags(anyList())).thenReturn(Set.of(taskDTO));

        mockMvc.perform(get("/tasks").param("tags", "tag1", "tag2"))
                .andExpect(status().isOk());

        verify(tagService, times(1)).getTasksHaveTags(anyList());
    }

    @Test
    @DisplayName("Получение задач по id юзера")
    void getTasksByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        when(taskService.getTasksByUserId(userId)).thenReturn(Set.of(taskDTO));

        mockMvc.perform(get("/tasks/user/" + userId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).getTasksByUserId(userId);
    }
}
