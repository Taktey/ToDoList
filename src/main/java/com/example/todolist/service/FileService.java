package com.example.todolist.service;

import com.example.todolist.Exceptions.AlreadyDeletedException;
import com.example.todolist.Exceptions.NoSuchFileException;
import com.example.todolist.dto.FileDto;
import com.example.todolist.models.FileEntity;
import com.example.todolist.repositories.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    private final String notFound = "File not found";
    private final String alreadyDeleted = "File already deleted";

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void softDeleteFile(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(notFound));
        if(fileEntity.getIsRemoved()){throw new AlreadyDeletedException(alreadyDeleted);}
        fileEntity.setIsRemoved(true);
        fileRepository.save(fileEntity);
    }

    public String getFileName(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(notFound));
        return fileEntity.getFileName();
    }

    public Long saveFile(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity(fileDto.getFileName());
        return fileRepository.save(fileEntity).getId();
    }

    public void assignFile(Long fileId, Long taskId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(notFound));
        fileEntity.setTaskId(taskId);
        fileRepository.save(fileEntity);
    }
}
