package com.studyplatform.server.repositories;

import com.studyplatform.server.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}