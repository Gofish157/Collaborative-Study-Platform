package com.studyplatform.server.controllers;

import com.studyplatform.server.models.User;
import com.studyplatform.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()){
            return "User Found: " + user.get().getName() + "(" + user.get().getEmail() + ")";
        } else {
            return "User Not Found";
        }

    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id){
        return "Update User ID: " + id;
    }

    @GetMapping
    public String getAllUsers(){
        return "Total Users: " + userService.getAllUsers().size();
    }
}
