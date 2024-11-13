package com.example.todolist.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate createdAt;
    private Boolean isRemoved;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TaskEntity> tasks;
}
