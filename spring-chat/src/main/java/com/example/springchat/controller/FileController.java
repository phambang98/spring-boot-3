package com.example.springchat.controller;


import com.example.springchat.service.FileStorageService;
import com.example.springcore.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("file/{fileName}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable("fileName") String fileName) throws IOException {
        FileModel fileModel = fileStorageService.readFileByName(fileName);
        return ResponseEntity.ok().contentType(fileModel.getMediaType()).body(fileModel.getByteArray());
    }
}
