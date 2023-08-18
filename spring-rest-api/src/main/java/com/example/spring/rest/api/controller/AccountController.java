package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.service.AccountService;
import com.example.core.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("signin")
    public ResponseEntity<ResultData> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.authenticate(loginRequest));
    }

    @PostMapping("signup")
    public ResponseEntity<ResultData> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(accountService.registerUser(signUpRequest));
    }

    @PostMapping("verify")
    public ResponseEntity<ResultData> verifyToken(@Valid @RequestBody TokenVerify tokenVerify) {
        return ResponseEntity.ok(accountService.verifyToken(tokenVerify));
    }

//    @PostMapping("/forgot")
//    public ResponseEntity<ResultData> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
//        return ResponseEntity.ok(userService.forgotPassword(request));
//    }

}
