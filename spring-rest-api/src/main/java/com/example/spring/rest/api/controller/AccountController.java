package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.security.jwt.TokenProvider;
import com.example.spring.rest.api.service.UsersService;
import com.example.core.enums.AuthProvider;
import com.example.core.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("account/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = tokenProvider.createToken(authentication);
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthResponse(token, userPrincipal.getName(), userPrincipal.getEmail(), userPrincipal.getImageUrl(), userPrincipal.getId()));
    }

    @PostMapping("account/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (Boolean.TRUE.equals(userService.existsByUserName(signUpRequest.getUserName()))) {
            return ResponseEntity.ok(new ApiResponse(false, "Email address already in use."));
        }
        // Creating user's account
        var user = new UserModel(null, signUpRequest.getUserName(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()), "", null, "", AuthProvider.local.name());

         userService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully@"));
    }

    @PostMapping("account/verify")
    public ResponseEntity<ApiResponse> verifyToken(@Valid @RequestBody TokenVerify tokenVerify) {

        return ResponseEntity.ok(new ApiResponse(tokenProvider.validateToken(tokenVerify.getAccessToken()), "User verify token successfully@"));
    }
}
