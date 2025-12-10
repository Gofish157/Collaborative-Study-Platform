package com.studyplatform.server.dto;

import com.studyplatform.server.models.Group;
import com.studyplatform.server.models.Task;

import java.time.LocalDateTime;

public record TaskDTO(Long id, String title, String description, String status, LocalDateTime deadline, Long groupId, Long userId, LocalDateTime createdAt) {
}
