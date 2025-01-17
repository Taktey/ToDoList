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
public class TaskDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long userId;
    private List<Long> files;
    private Set<String> tags;

    public TaskDTO(LocalDate startDate, LocalDate endDate, String description, Long userId, Set<String> tags) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.userId = userId;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "{" +
                "\n id=" + id + "" +
                ",\n startDate=" + startDate +
                ",\n endDate=" + endDate +
                ",\n description='" + description + '\'' +
                ",\n userId=" + userId +
                ",\n files=" + files +
                ",\n tags=" + tags +
                "\n}";
    }

    public static class Builder {
        private Long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private Long userId;
        private List<Long> fileIds;
        private Set<String> tags;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder fileIds(List<Long> fileIds) {
            this.fileIds = fileIds;
            return this;
        }

        public Builder tags(Set<String> tags) {
            this.tags = tags;
            return this;
        }

        public TaskDTO build() {
            return new TaskDTO(id, startDate, endDate, description, userId, fileIds, tags);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

/*{
        "startDate":"2024-11-01",
        "endDate":"2024-11-15",
        "description":"khvhvhkghgkg",
        "userId":12345
        }*/
