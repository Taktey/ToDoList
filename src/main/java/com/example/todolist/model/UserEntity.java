package com.example.todolist.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "removed")
    private Boolean removed = false;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<TaskEntity> tasks;

    public static class Builder {
        private UUID id;
        private String name;
        private LocalDate createdAt;
        private Boolean removed = false;
        private List<TaskEntity> tasks;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder removed(Boolean removed) {
            this.removed = removed;
            return this;
        }

        public Builder tasks(List<TaskEntity> tasks) {
            this.tasks = tasks;
            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();
            userEntity.id = this.id;
            userEntity.name = this.name;
            userEntity.createdAt = this.createdAt;
            userEntity.removed = this.removed;
            userEntity.tasks = this.tasks;
            return userEntity;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
