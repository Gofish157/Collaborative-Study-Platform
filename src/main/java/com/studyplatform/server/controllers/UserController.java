package com.studyplatform.server.controllers;

import com.studyplatform.server.dto.UpdateUserDTO;
import com.studyplatform.server.dto.UserDTO;
import com.studyplatform.server.models.User;
import com.studyplatform.server.security.UserDetailsImpl;
import com.studyplatform.server.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getBio());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateMe(Authentication authentication, @RequestBody UpdateUserDTO updateUserDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (updateUserDTO.name() != null) {user.setName(updateUserDTO.name());}
        if (updateUserDTO.email() != null) {user.setEmail(updateUserDTO.email());}
        if (updateUserDTO.bio() != null) {user.setBio(updateUserDTO.bio());}
        User updatedUser = userService.updateUser(user);
        UserDTO userDTO = new UserDTO(updatedUser.getName(), updatedUser.getEmail(), updatedUser.getBio());
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/me")
    public ResponseEntity<UserDTO> deleteMe(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        userService.deleteUser(user.getId());
        UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getBio());
        return ResponseEntity.ok(userDTO);
    }

}
