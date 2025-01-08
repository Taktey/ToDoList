package com.example.todolist.controllers;

import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.UserCreateDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.models.UserEntity;
import com.example.todolist.service.UserService;
import com.example.todolist.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserEntity user = UserMapper.userDtoToEntity(userCreateDto);
        Long id = userService.createUser(user);
        return new ResponseEntity<>("User successfully created, user id = "+id, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        try {
            UserDto userDto = UserMapper.userEntityToDto(userService.getUserById(userId));
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch(NoSuchUserFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteById(userId);
            return new ResponseEntity<>("User successfully deleted, user id = "+userId, HttpStatus.OK);
        } catch(NoSuchUserFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/restore/{userId}")
    public ResponseEntity<?> restoreUser(@PathVariable Long userId) {
        try {
            userService.restoreById(userId);
            return new ResponseEntity<>("User successfully restored, user id = "+userId, HttpStatus.OK);
        } catch(NoSuchUserFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
