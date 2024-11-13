package com.example.todolist.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String tags;
    private Boolean isRemoved;
    private Long userId;
    @OneToMany(mappedBy = "taskId", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<FileEntity> files;
}
