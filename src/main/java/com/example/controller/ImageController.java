package com.example.controller;

import com.example.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class ImageController {

    @GetMapping("ping")
    public String ping() {
        return "ok";
    }

    @GetMapping(value = "/image2")
    public ResponseEntity<byte[]> getImage() throws IOException {
        byte[] image = IOUtils.toByteArray(getClass().getResourceAsStream("/cc.jpg"));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

}
