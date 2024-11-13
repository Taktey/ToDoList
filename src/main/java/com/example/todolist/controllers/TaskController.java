package com.example.todolist.controllers;


import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.service.TaskService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Data
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        TaskDto task;
        try {
            task = taskService.getTaskById(taskId);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
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
                "Task with id = "+userId+" reassigned to user id = "+userId,
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        Long taskId;
        try {
            taskId = taskService.createTask(taskDto);
        } catch(NoSuchUserFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Task successfully created, id = " + taskId, HttpStatus.CREATED);

    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId,
                                        @RequestBody TaskDto taskDto) {
        try {
            taskService.updateTask(taskId, taskDto);
        } catch (NoSuchTaskFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "Task with id = "+taskId+" successfully updated.",
                HttpStatus.OK);
    }
    @PutMapping("/addTag/{taskId}") //["string1", "string2", "string3"]
    public ResponseEntity<?> addTags(@PathVariable Long taskId, @RequestBody List<String> tags){
        Set<String> tagsAfterAdding;
        try {
            tagsAfterAdding = taskService.addTags(taskId, tags);
        } catch (NoSuchTaskFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Теги добавлены, теги задачи:\n"+tagsAfterAdding, HttpStatus.OK);
    }
    @PostMapping("/byTags") //?strings=string1,string2,string3
    public ResponseEntity<?> findByTags(@RequestParam List<String> tags){
        List<TaskDto> tasks = taskService.getTasksByTags(tags);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
        } catch (NoSuchTaskFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Task with id = "+taskId+" successfully removed.",
                HttpStatus.OK);
    }


}
