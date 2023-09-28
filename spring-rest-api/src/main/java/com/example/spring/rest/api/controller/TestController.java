package com.example.spring.rest.api.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("")
    public String getSessionIdFromRequest(HttpSession httpSession) {
        return httpSession.getId();
    }
}
