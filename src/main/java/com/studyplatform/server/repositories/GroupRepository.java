package com.studyplatform.server.repositories;

import com.studyplatform.server.models.Group;

public interface GroupRepository {
    Group save(Group group);
    Group findById(Long id);
    void deleteById(Long id);
}
