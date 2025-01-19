package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class AssignTasksToUserDTO {
    private Set<UUID> taskIds;
    private UUID userId;
}
