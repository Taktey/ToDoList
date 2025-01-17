package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    private Long id;
    private String name;

    public TagDTO(String name){
        this.name = name;
    }
}
