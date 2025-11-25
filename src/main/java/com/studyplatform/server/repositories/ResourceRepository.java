package com.studyplatform.server.repositories;

import com.studyplatform.server.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByGroupId(Long groupId);
}