package com.example.todolist.service;

import com.example.todolist.dto.FileDTO;
import com.example.todolist.exception.AlreadyDeletedException;
import com.example.todolist.exception.NoSuchFileException;
import com.example.todolist.exception.NoSuchTaskFoundException;
import com.example.todolist.model.FileEntity;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.repository.FileRepository;
import com.example.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileService {
    private static final String FILE_DIRECTORY = "files/";
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;

    public void softDelete(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndRemovedIsTrue(fileId)
                .orElseThrow(NoSuchFileException::new);
        if (fileEntity.getRemoved()) {
            throw new AlreadyDeletedException();
        }
        fileEntity.setRemoved(true);
        fileRepository.save(fileEntity);
    }

    public void restore(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndRemovedIsTrue(fileId)
                .orElseThrow(NoSuchFileException::new);
        fileEntity.setRemoved(false);
        fileRepository.save(fileEntity);
    }

    public String getFileName(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndRemovedIsFalse(fileId)
                .orElseThrow(NoSuchFileException::new);
        return fileEntity.getFileName();
    }

    public FileDTO saveFile(FileDTO fileDto) {
        FileEntity fileEntity = new FileEntity(fileDto.getFileName());
        FileEntity savedFile = fileRepository.save(fileEntity);
        return new FileDTO(savedFile.getFileName(), savedFile.getId());
    }

    public void assignFile(Long fileId, Long taskId) {
        FileEntity fileEntity = fileRepository.findByIdAndRemovedIsFalse(fileId)
                .orElseThrow(NoSuchFileException::new);
        TaskEntity taskEntity = taskRepository.findByIdAndRemovedIsFalse(taskId)
                .orElseThrow(NoSuchTaskFoundException::new);
        fileEntity.setTask(taskEntity);
        fileRepository.save(fileEntity);
    }

    public Resource download(Long id) {
        return new ClassPathResource(FILE_DIRECTORY + getFileName(id));
    }

    public FileDTO upload(MultipartFile file, Long taskId) {
        String fileName = file.getOriginalFilename();
        String filePath = FILE_DIRECTORY + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            return new FileDTO(e.getMessage(), -1L);
        }
        FileDTO fileDto = new FileDTO(fileName, taskId);
        return saveFile(fileDto);
    }
}
