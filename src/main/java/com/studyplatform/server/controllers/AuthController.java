package com.studyplatform.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public String registerUser(){
        return "Registration endpoint";
    }

    @PostMapping("/login")
    public String loginUser(){
        return "Login endpoint";
    }
}
