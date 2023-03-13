package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebFluxController {

    Logger logger = LoggerFactory.getLogger(WebFluxController.class);
    @GetMapping(value = "/film")
    public void sendRedirect(@PathVariable String title) {
        logger.info("sendRedirect video :{}", title);
    }

    @GetMapping(value = "html-view")
    public String index() {
        return "index";
    }


}
