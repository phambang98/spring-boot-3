package com.example.spring.rest.api.service;

import com.example.core.enums.AuthProvider;
import com.example.core.model.*;
import com.example.core.repository.RefreshTokenRepository;
import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class AccountService {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    public ResultData authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var token = tokenProvider.createToken(authentication);
            var userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return ResultData.builder().data(new AuthResponse(token, userPrincipal.getName(), userPrincipal.getEmail(),
                    userPrincipal.getImageUrl(), userPrincipal.getId())).build();
        } catch (AuthenticationException ex) {
            log.error("", ex);
            return ResultData.builder().success(false).message(ex.getMessage()).build();
        }

    }

    @Transactional
    public ResultData registerUser(SignUpRequest signUpRequest) {
        if (Boolean.TRUE.equals(userService.existsByUserName(signUpRequest.getUserName()))) {
            return ResultData.builder().success(false).message("Email address already in use.").build();
        }
        // Creating user's account
        var user = new UserModel(null, signUpRequest.getUserName(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()), "", null, "", AuthProvider.local.name());
        userService.saveUser(user);
        return ResultData.builder().message("User registered successfully@").build();
    }

    public ResultData verifyToken(TokenVerify tokenVerify) {
        if (tokenProvider.validateToken(tokenVerify.getAccessToken())) {
            return ResultData.builder().message("User verify token successfully@").build();
        }
        return ResultData.builder().success(false).message("User verify token failure").build();
    }

}
