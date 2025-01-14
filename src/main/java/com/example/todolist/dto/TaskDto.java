package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long userId;
    private List<Long> files;
    private Set<String> tags;

    @Override
    public String toString() {
        return "{" +
                "\n id=" + id + ""+
                ",\n startDate=" + startDate +
                ",\n endDate=" + endDate +
                ",\n description='" + description + '\'' +
                ",\n userId=" + userId +
                ",\n files=" + files +
                ",\n tags=" + tags +
                "\n}";
    }
}

/*{
        "startDate":"2024-11-01",
        "endDate":"2024-11-15",
        "description":"khvhvhkghgkg",
        "userId":12345
        }*/
