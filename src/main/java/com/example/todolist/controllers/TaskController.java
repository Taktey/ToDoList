package com.example.todolist.controllers;


import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TaskEntity;
import com.example.todolist.service.TaskService;
import com.example.todolist.util.TaskMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        TaskEntity task;
        try {
            task = taskService.getTaskById(taskId);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(TaskMapper.taskEntityToDto(task), HttpStatus.OK);
    }

    @PostMapping("/{taskId}/to/{userId}")
    public ResponseEntity<?> reAssignTask(@PathVariable Long taskId,
                                          @PathVariable Long userId) {
        try {
            taskService.reAssignTask(taskId, userId);
        } catch (NoSuchTaskFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "Task with id = "+userId+" reassigned to user id = "+userId,
                HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        TaskEntity task = TaskMapper.taskDtoToEntity(taskDto);
        Long id = taskService.createTask(task);
        return new ResponseEntity<>("Task successfully created, id = " + id, HttpStatus.CREATED);

    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId,
                                        @RequestBody TaskDto taskDto) {
        try {
            taskService.updateTask(taskId, TaskMapper.taskDtoToEntity(taskDto));
        } catch (NoSuchTaskFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "Task with id = "+taskId+" successfully updated.",
                HttpStatus.OK);
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
