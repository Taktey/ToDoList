package com.example.todolist.controllers;

import com.example.todolist.models.User;
import com.example.todolist.service.UserService;
import com.example.todolist.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(String userName) {
        User user = UserMapper.userDtoToEntity(userName);
        Long id = userService.createUser(user);
        return new ResponseEntity<>("User successfully created, user id = "+id, HttpStatus.OK);
    }
}
