package com.studyplatform.server.controllers;

import com.studyplatform.server.models.User;
import com.studyplatform.server.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(){
        try {
            User user = authService.registerUser("Test User", "test@example.com" , "password123");
            return "User Registered: " + user.getName() + " (" + user.getEmail() + ")";
        } catch (Exception e) {
            return "Registration Failed: " + e.getMessage();

        }
    }

    @GetMapping("/login")
    public String loginPage(){
        return "Login Page";
    }

    @PostMapping("/login")
    public String loginUser() {
        try {
            User user = authService.loginUser("test@example.com", "password123");
            return "User Logged In: " + user.getName() + " (" + user.getEmail() + ")";
        } catch (Exception e) {
            return "Login Failed: " + e.getMessage();
        }
    }
}
