package com.example.todolist.controller;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.service.UserService;
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

import java.util.UUID;

import static org.mockito.Mockito.any;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private UUID userId;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userId = UUID.randomUUID();
        userDTO = new UserDTO(userId, "Test User");
    }

    @Test
    @DisplayName("Создание пользователя")
    void createUser() throws Exception {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    void getUserById() throws Exception {
        when(userService.getUserDTOById(userId)).thenReturn(userDTO);

        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(userService, times(1)).getUserDTOById(userId);
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteById(userId);

        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Восстановление пользователя")
    void restoreUser() throws Exception {
        doNothing().when(userService).restoreById(userId);

        mockMvc.perform(put("/users/" + userId + "/restore"))
                .andExpect(status().isOk());

        verify(userService, times(1)).restoreById(userId);
    }
}
