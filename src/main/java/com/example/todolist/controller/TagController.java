package com.example.todolist.controller;

import com.example.todolist.dto.TagDTO;
import com.example.todolist.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public Set<TagDTO> getAll() {
        return tagService.getAllTags();
    }

    @PostMapping
    public TagDTO create(@RequestBody TagDTO tagDTO) {
        return tagService.createTag(tagDTO);
    }
}