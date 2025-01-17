package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    private UUID id;
    private String name;

    public TagDTO(String name){
        this.name = name;
    }
}
