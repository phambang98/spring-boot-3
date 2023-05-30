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
public class Oauth2FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
//        PrintWriter out = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        out.print("Login failure!");
//        out.print(" with " + exception.getMessage());
//        out.flush();
        HttpSession session = request.getSession();
        session.setAttribute(Keys.AUTHORIZATION_REQUEST_NOT_FOUND.name(), exception.getMessage());
        response.sendRedirect("/login");
    }
}
