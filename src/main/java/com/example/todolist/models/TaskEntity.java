package com.example.todolist.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_entity")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    public TaskEntity(
            LocalDate startDate,
            LocalDate endDate,
            String description,
            Set<String> tags,
            UserEntity user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.tags = tags;
        this.isRemoved = false;
        this.user = user;
    }

    @ElementCollection
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @Column(name = "is_removed")
    private Boolean isRemoved;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Имя столбца внешнего ключа
    private UserEntity user;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FileEntity> files;
}
