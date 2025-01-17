package com.example.todolist.repository;

import com.example.todolist.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

    Optional<FileEntity> findByIdAndRemovedIsFalse(Long fileId);

    Optional<FileEntity> findByIdAndRemovedIsTrue(Long fileId);
}