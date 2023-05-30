package com.example.springmvc.controller;

import com.example.springcore.entity.Users;
import com.example.springcore.enums.Keys;
import com.example.springcore.model.UsersSignUpModel;
import com.example.springcore.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "login")
    public String login(Model model, HttpSession session) {
        model.addAttribute("usersSignUpModel", new UsersSignUpModel());
        model.addAttribute("userNameError", session.getAttribute(Keys.USER_NAME_NOT_FOUND.name()));
        model.addAttribute("passwordError", session.getAttribute(Keys.INCORRECT_PASSWORD.name()));
        model.addAttribute("authorizationRequestNotFound", session.getAttribute(Keys.AUTHORIZATION_REQUEST_NOT_FOUND.name()));
        session.removeAttribute(Keys.USER_NAME_NOT_FOUND.name());
        session.removeAttribute(Keys.INCORRECT_PASSWORD.name());
        return "login";
    }

    @GetMapping(value = {"home", "/*"})
    public String home() {
        return "home";
    }

    @GetMapping(value = {"/error"})
    public String handle() {
        return "404";
    }

    @PostMapping("/sign-up")
    public String authenticate(@ModelAttribute("usersSignUpModel") UsersSignUpModel usersSignUpModel, HttpServletRequest request, Model model) {
        String userName = usersSignUpModel.getUserName();
        String email = usersSignUpModel.getEmail();
        String password = usersSignUpModel.getPassword();
        String confirmPassword = usersSignUpModel.getConfirmPassword();
        if (!StringUtils.equals(password, confirmPassword)) {
            model.addAttribute("errorPassword", "Password And Confirm Password does not match!");
            return "login";
        }
        if (usersRepository.existsByUserName(userName)) {
            model.addAttribute("errorUserName", "User name already exist!");
            return "login";
        }
        Users users = new Users();
        users.setEmail(email);
        users.setUserName(userName);
        users.setPassword(password);
        users.setStatus("A");
        users.setRoles(userName);
        users = usersRepository.save(users);
        request.getSession().setAttribute("usersSession", users);
        model.addAttribute("userName", userName);
        model.addAttribute("password", password);
        return "login";
    }
}
