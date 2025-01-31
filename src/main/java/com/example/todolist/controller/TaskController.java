package com.example.todolist.controller;


import com.example.todolist.dto.TagsToTaskAssignDTO;
import com.example.todolist.dto.TaskDTO;
import com.example.todolist.dto.TasksToUserAssignDTO;
import com.example.todolist.service.TagService;
import com.example.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TagService tagService;

    @GetMapping("/{id}")
    public TaskDTO get(@PathVariable UUID id) {
        return taskService.getTaskDTOById(id);
    }

    @PutMapping("/assign/user")
    public void assign(@RequestBody TasksToUserAssignDTO dto) {
        taskService.assignTasks(dto);
    }

    @PostMapping
    public TaskDTO create(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping
    public TaskDTO update(@RequestBody TaskDTO taskDto) {
        return taskService.updateTask(taskDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}/restore")
    public void restore(@PathVariable UUID id) {
        taskService.restoreTask(id);
    }

    @GetMapping()
    public Set<TaskDTO> getByTags(@RequestParam List<String> tags) {
        return tagService.getTasksHaveTags(tags);
    }

    @PutMapping("/assign/tags")
    public TaskDTO assignToTask(@RequestBody TagsToTaskAssignDTO dto) {
        return taskService.assignTagsToTask(dto.getTagNames(), dto.getTaskId());
    }

    @GetMapping("/user/{id}")
    public Set<TaskDTO> getTasksByUserId(@PathVariable UUID id) {
        return taskService.getTasksByUserId(id);
    }
}
