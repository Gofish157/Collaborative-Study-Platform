package com.studyplatform.server.services;

import com.studyplatform.server.models.User;
import com.studyplatform.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User registerUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid login or password"));
        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        throw new RuntimeException("Invalid login or password");
    }

}
