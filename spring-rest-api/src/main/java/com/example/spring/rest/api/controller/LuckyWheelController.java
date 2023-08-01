package com.example.spring.rest.api.controller;

import com.example.core.entity.PrizeGroup;
import com.example.core.entity.Prizes;
import com.example.spring.rest.api.model.LuckyWheelModel;
import com.example.spring.rest.api.service.LuckyWheelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lucky-wheel")
public class LuckyWheelController {

    @Autowired
    private LuckyWheelService luckyWheelService;


    @RequestMapping("")
    public PrizeGroup findFirstByCurrentDateTime() {
        return luckyWheelService.findFirstByCurrentDateTime();
    }


    @PostMapping("spin/{prizeGroupId}")
    public LuckyWheelModel spinWheel(@PathVariable("prizeGroupId") Long prizeGroupId) {
        return luckyWheelService.spinWheel(prizeGroupId);
    }

}
