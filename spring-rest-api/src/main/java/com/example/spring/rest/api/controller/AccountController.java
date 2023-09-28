package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.model.AddCreditModel;
import com.example.spring.rest.api.service.AccountService;
import com.example.core.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("signin")
    public ResponseEntity<ResultData> authenticate(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return ResponseEntity.ok(accountService.authenticate(loginRequest,request.getSession()));
    }

    @PostMapping("signup")
    public ResponseEntity<ResultData> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(accountService.registerUser(signUpRequest));
    }

    @PostMapping("verify")
    public ResponseEntity<ResultData> verifyToken(@Valid @RequestBody TokenVerify tokenVerify) {
        return ResponseEntity.ok(accountService.verifyToken(tokenVerify));
    }

    @PostMapping("/add-credit")
    public ResponseEntity<ResultData> addCredit(@Valid @RequestBody AddCreditModel request) {
        return ResponseEntity.ok(accountService.addCredit(request));
    }

    @GetMapping("/captcha")
    public ResponseEntity<ResultData> captcha(HttpServletRequest  request) {
        return ResponseEntity.ok(accountService.captcha(request.getSession()));
    }

//    @PostMapping("/forgot-password")
//    public ResponseEntity<ResultData> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
//        return ResponseEntity.ok(accountService.forgotPassword(request));
//    }

}
