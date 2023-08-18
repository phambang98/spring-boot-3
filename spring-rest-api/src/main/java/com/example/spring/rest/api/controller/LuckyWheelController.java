package com.example.spring.rest.api.controller;

import com.example.core.entity.PrizeGroup;
import com.example.core.entity.Prizes;
import com.example.core.model.ResultData;
import com.example.spring.rest.api.model.LuckyWheelModel;
import com.example.spring.rest.api.service.LuckyWheelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lucky-wheel")
@SecurityRequirements({@SecurityRequirement(name = "GoogleOauth2"),
        @SecurityRequirement(name = "bearerAuth")}
)
public class LuckyWheelController {

    @Autowired
    private LuckyWheelService luckyWheelService;


    @RequestMapping("")
    public ResponseEntity<ResultData> findFirstByCurrentDateTime() {
        return new ResponseEntity<>(luckyWheelService.findFirstByCurrentDateTime(), HttpStatus.OK);
    }


    @PostMapping("spin/{prizeGroupId}")
    public ResponseEntity<ResultData> spinWheel(@PathVariable("prizeGroupId") Long prizeGroupId) {
        return new ResponseEntity<>(luckyWheelService.spinWheel(prizeGroupId), HttpStatus.OK);
    }

}
