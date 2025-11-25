package com.studyplatform.server.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "group_id", nullable = false)
    private Long groupId;
    @Column(length = 1000)
    private String description;
    @Column
    private String status;
    @Column(nullable = true)
    private String deadline;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Task() {}

    public Task(String title, String description, String deadline, Long groupId){
        this.title = title;
        this.description = description;
        this.status = "pending";
        this.deadline = deadline;
        this.groupId = groupId;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
