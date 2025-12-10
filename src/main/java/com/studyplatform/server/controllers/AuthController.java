package com.studyplatform.server.controllers;

import com.studyplatform.server.dto.AuthRequest;
import com.studyplatform.server.dto.AuthResponse;
import com.studyplatform.server.dto.RegisterUserDTO;
import com.studyplatform.server.models.User;
import com.studyplatform.server.security.JwtUtil;
import com.studyplatform.server.services.AuthService;
import com.studyplatform.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.studyplatform.server.dto.UserDTO;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterUserDTO userDTO) {
        try {
            User user = authService.registerUser(userDTO.name(), userDTO.email(), userDTO.password());
            return "User Registered: " + user.getName() + " (" + user.getEmail() + ")";
        } catch (Exception e) {
            return "Registration Failed: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user;

        try {
            user = userService.getUserByEmail(request.email());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }

}

