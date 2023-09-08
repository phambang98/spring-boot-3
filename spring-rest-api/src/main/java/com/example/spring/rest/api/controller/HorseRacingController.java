package com.example.spring.rest.api.controller;

import com.example.core.model.ResultData;
import com.example.spring.rest.api.service.HorseRacingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/horse-racing")
public class HorseRacingController {

    @Autowired
    private HorseRacingService horseRacingService;

    @GetMapping("")
    public ResponseEntity<ResultData> getAllHorseRacingByHorseRacingGroupId() {
        return ResponseEntity.ok(horseRacingService.getAllHorseRacingByHorseRacingGroupId());
    }


}
