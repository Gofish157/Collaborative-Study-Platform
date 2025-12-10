package com.studyplatform.server.dto;

import com.studyplatform.server.models.Group;

import java.time.LocalDateTime;

public record GroupDTO(Long id, String name, String description, String createdBy, LocalDateTime createdAt) {
    public static GroupDTO from(Group group) {
        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedBy(),
                group.getCreatedAt()
        );
    }
}
