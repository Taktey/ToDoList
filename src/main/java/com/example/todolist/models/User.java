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
public class User {
    @Id
    Long id;
    String name;
    LocalDate createdAt;
    Boolean isRemoved;
    @OneToMany(mappedBy = "executorId", fetch = FetchType.LAZY)
    @ToString.Exclude
    List<Task> tasks;
}
