package com.example.todolist.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_entity")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "is_removed")
    private Boolean isRemoved;

    public FileEntity(String fileName, TaskEntity task) {
        this.task = task;
        this.fileName = fileName;
        this.isRemoved = false;
    }
}
