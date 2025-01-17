package com.example.todolist.controller;

import com.example.todolist.exception.NoSuchUserFoundException;
import com.example.todolist.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /*@Test
    void testCreateUser_Success() throws Exception {
        when(userService.createUser(any(UserEntity.class))).thenReturn(1L);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User successfully created, user id = 1")));

        verify(userService, times(1)).createUser(any(UserEntity.class));
    }*/

    /*@Test
    void testGetUser_Success() throws Exception {
        UserDTO userDto = new UserDTO(1L, "Тестовое имя",null);
        when(userService.getUserById(1L)).thenReturn(UserMapper.userDtoToEntity(new UserCreateDTO(userDto.getName())));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Тестовое имя"));

        verify(userService, times(1)).getUserById(1L);
    }*/

    @Test
    void testGetUser_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new NoSuchUserFoundException("Пользователь не найден"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Пользователь не найден"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully deleted, user id = 1"));

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new NoSuchUserFoundException("Пользователь не найден")).when(userService).deleteById(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Пользователь не найден"));

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testRestoreUser_Success() throws Exception {
        doNothing().when(userService).restoreById(1L);

        mockMvc.perform(post("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully restored, user id = 1"));

        verify(userService, times(1)).restoreById(1L);
    }

    @Test
    void testRestoreUser_NotFound() throws Exception {
        doThrow(new NoSuchUserFoundException("Пользователь не найден")).when(userService).restoreById(1L);

        mockMvc.perform(post("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Пользователь не найден"));

        verify(userService, times(1)).restoreById(1L);
    }
}