package com.example.springapi.controller;

import com.example.springapi.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    @GetMapping("ping")
    public String ping() {
        SecurityUtils.getCurrentUserLogin();
        return "osssk";
    }

    @GetMapping(value = "/image2")
    public ResponseEntity<byte[]> getImage() throws IOException {
        SecurityUtils.getCurrentUserLogin();
        byte[] image = IOUtils.toByteArray(getClass().getResourceAsStream("/image/cc.jpg"));
//         image = IOUtils.toByteArray(new FileInputStream("E:/2.png"));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

}
