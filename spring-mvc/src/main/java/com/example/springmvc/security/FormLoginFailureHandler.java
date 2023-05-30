package com.example.springmvc.security;

import com.example.springcore.enums.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class FormLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpSession session = request.getSession();
        if (Keys.USER_NAME_NOT_FOUND.getDescription().equals(exception.getMessage())) {
            session.setAttribute(Keys.USER_NAME_NOT_FOUND.name(), Keys.USER_NAME_NOT_FOUND.getDescription());
        } else if (Keys.INCORRECT_PASSWORD.getDescription().equals(exception.getMessage())) {
            session.setAttribute(Keys.INCORRECT_PASSWORD.name(), Keys.INCORRECT_PASSWORD.getDescription());
        }
        response.sendRedirect("/login");
    }
}
