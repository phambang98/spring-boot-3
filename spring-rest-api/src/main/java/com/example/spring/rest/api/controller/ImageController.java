package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
