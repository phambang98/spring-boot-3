package com.example.spring.rest.api;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AfterRun implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("after run done");
    }
}
