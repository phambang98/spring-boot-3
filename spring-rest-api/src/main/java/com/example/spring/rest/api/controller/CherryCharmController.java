package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.service.CherryCharmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cherry-charm")
public class CherryCharmController {

    @Autowired
    private CherryCharmService cherryCharmService;

    @GetMapping("")
    public ResponseEntity<Void> getAllCherryCharm() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("winning")
    public ResponseEntity<Void> getWinningCherryCharm() {
        return ResponseEntity.ok().build();
    }


}
