package com.studyplatform.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Study Platform!";
    }
}
