package com.example.todolist.controllers;

import com.example.todolist.Exceptions.AlreadyDeletedException;
import com.example.todolist.Exceptions.NoSuchFileException;
import com.example.todolist.dto.FileDto;
import com.example.todolist.service.FileService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Data
@RestController
@RequestMapping(value = "/file")
public class FileController {
    private final FileService fileService;
    private final String fileDir = "files/";

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        String fileName;
        Resource resource;
        HttpHeaders headers = new HttpHeaders();
        try {
            fileName = fileService.getFileName(id);
            resource = new ClassPathResource(fileDir + fileName);
        } catch (NoSuchFileException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        try {
            fileService.softDeleteFile(id);
        } catch (NoSuchFileException | AlreadyDeletedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("File with id " + id + " deleted", HttpStatus.OK);
    }

    @PostMapping("/{fileId}/{taskId}")
    public ResponseEntity<String> assignFile(@PathVariable Long fileId,
                                             @PathVariable Long taskId){
        try {
            fileService.assignFile(fileId, taskId);
        } catch (NoSuchFileException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("File assigned to task "+taskId, HttpStatus.OK);
    }
    @PostMapping("/upload/")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file,
                                             @RequestPart("taskId") String taskId) {
        Long fileId;
        Long taskIdLong = Long.getLong(taskId);
        String fileName;
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Файл пустой");
        }
        fileName = file.getOriginalFilename();
        try {
            String filePath = fileDir + fileName;
            file.transferTo(new File(filePath));
            FileDto fileDto = new FileDto(fileName, taskIdLong);
            fileId = fileService.saveFile(fileDto);
            return ResponseEntity.ok("Файл успешно загружен: " + fileName +
                    "\nid= " + fileId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}

