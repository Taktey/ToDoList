package com.example.todolist.controller;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.dto.UserDTO;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}/restore")
    public void restore(@PathVariable UUID id) {
        userService.restoreById(id);
    }

    @GetMapping("/{id}/tasks")
    public Set<TaskDTO> tasks(@PathVariable UUID id){
        return  userService.tasksByUserId(id);
    }
}
