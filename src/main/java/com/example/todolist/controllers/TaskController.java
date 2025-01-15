package com.example.todolist.controllers;


import com.example.todolist.Exceptions.NoSuchTagFoundException;
import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.TaskCreateDto;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.service.TagService;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    private final TaskService taskService;
    private final TagService tagService;

    @Autowired
    public TaskController(TaskService taskService, TagService tagService) {
        this.taskService = taskService;
        this.tagService = tagService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        try {
            return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{taskId}/to/{userId}")
    public ResponseEntity<?> assignTask(@PathVariable Long taskId,
                                        @PathVariable Long userId) {
        try {
            taskService.assignTask(taskId, userId);
        } catch (NoSuchTaskFoundException | NoSuchUserFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "Task with id = " + userId + " reassigned to user id = " + userId,
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateDto taskCreateDto) {
        try {
            return new ResponseEntity<>("Task successfully created:\n"
                    + taskService.createTask(taskCreateDto), HttpStatus.CREATED);
        } catch (NoSuchUserFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto) {
        if (taskDto.getUserId() != null) return new ResponseEntity<>(
                "Операция изменения не выполнена,\n" +
                        "мспользуйте POST /task/{taskId}to{userId}", HttpStatus.METHOD_NOT_ALLOWED);
        try {
            taskService.updateTask(taskDto);
            return new ResponseEntity<>(
                    "Task with id = " + taskDto.getId() + " successfully updated.",
                    HttpStatus.OK);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("Task with id = " + taskId + " successfully removed.",
                    HttpStatus.OK);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/restore/{taskId}")
    public ResponseEntity<?> restoreTask(@PathVariable Long taskId) {
        try {
            taskService.restoreTask(taskId);
            return new ResponseEntity<>("Task with id = " + taskId + " successfully restored.",
                    HttpStatus.OK);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<?> getTasksByTags(@RequestParam List<String> tags) {
        try {
            return new ResponseEntity<>(tagService.getTasksHaveTags(tags), HttpStatus.OK);
        } catch (NoSuchTagFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
