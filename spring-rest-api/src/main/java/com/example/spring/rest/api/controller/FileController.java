package com.example.spring.rest.api.controller;


import com.example.core.utils.FileUtils;
import com.example.spring.rest.api.service.FileStorageService;
import com.example.core.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable("fileName") String fileName) {
        FileModel fileModel = fileStorageService.readFileByName(fileName);
        return ResponseEntity.ok().contentType(fileModel.getMediaType()).body(fileModel.getByteArray());
    }

    @GetMapping(value = "/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) throws IOException {
        Resource resource = new ClassPathResource(String.format("image/%s", fileName));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource.getContentAsByteArray());
    }

    @GetMapping("/download-file/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.TEXT_HTML)
                .contentLength(resource.contentLength())
                .body(resource);

    }

}
