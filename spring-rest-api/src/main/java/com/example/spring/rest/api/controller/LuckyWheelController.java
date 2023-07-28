package com.example.spring.rest.api.controller;

import com.example.core.entity.Prizes;
import com.example.spring.rest.api.model.ResultData;
import com.example.spring.rest.api.service.LuckyWheelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lucky-wheel")
public class LuckyWheelController {

    @Autowired
    private LuckyWheelService luckyWheelService;


    @RequestMapping("")
    public List<Prizes> getAllPrizeByCurrentDate() {
        return luckyWheelService.getAllPrizeByCurrentDate();
    }


    @PostMapping("spin")
    public ResultData spinWheel(@RequestParam("prizeGroupId") Long prizeGroupId) {
        return luckyWheelService.spinWheel(prizeGroupId);
    }

}
