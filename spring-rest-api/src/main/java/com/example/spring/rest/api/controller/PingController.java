package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping("")
    public ResponseEntity<String> ping() {
        SecurityUtils.getCurrentUserLogin();
        return ResponseEntity.ok().build();
    }
}
