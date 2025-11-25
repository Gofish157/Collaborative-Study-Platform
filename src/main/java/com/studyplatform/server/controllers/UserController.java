package com.studyplatform.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id){
        return "User ID: " + id;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id){
        return "Update User ID: " + id;
    }
}
