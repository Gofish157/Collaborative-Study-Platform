package com.studyplatform.server.repositories;

import com.studyplatform.server.models.User;

public interface UserRepository {
    User save(User user);
    User findById(Long id);
    User findByEmail(String email);
    void deleteById(Long id);
}
