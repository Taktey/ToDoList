package com.example.todolist.controllers;

import com.example.todolist.Exceptions.NoSuchTagFoundException;
import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.dto.TagCreateDto;
import com.example.todolist.service.TagService;
import com.example.todolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final TaskService taskService;

    public TagController(TagService tagService, TaskService taskService) {
        this.tagService = tagService;
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTags(){
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTag(@RequestBody TagCreateDto tagCreateDto) {
        try {
            return new ResponseEntity<>(tagService.createTag(tagCreateDto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{tagName}/to/{taskId}")
    public ResponseEntity<?> assignTagToTask(@PathVariable String tagName,
                                             @PathVariable Long taskId){
        try{
            return new ResponseEntity<>(taskService.assignTagToTask(tagName,taskId),HttpStatus.OK);
        } catch (NoSuchTaskFoundException | NoSuchTagFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
