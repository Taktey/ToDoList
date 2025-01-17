package com.example.todolist.controller;

import com.example.todolist.dto.FileDTO;
import com.example.todolist.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        return new ResponseEntity<>(fileService.download(id), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fileService.softDelete(id);
    }

    @PostMapping("/restore/{id}")
    public void restore(@PathVariable Long id) {
        fileService.restore(id);
    }

    @PostMapping("/{fileId}/{taskId}")
    public void assignToTask(@PathVariable Long fileId,
                             @PathVariable Long taskId) {
        fileService.assignFile(fileId, taskId);
    }

    @PostMapping
    public FileDTO upload(@RequestPart("file") MultipartFile file,
                          @RequestPart("taskId") String taskId) {
        return fileService.upload(file, Long.getLong(taskId));
    }
}

