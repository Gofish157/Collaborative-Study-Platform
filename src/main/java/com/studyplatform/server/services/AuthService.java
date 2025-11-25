package com.studyplatform.server.services;

import com.studyplatform.server.models.User;
import com.studyplatform.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password); // TODO: позже добавить хеширование
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        throw new RuntimeException("Invalid login or password");
    }

}
