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
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String dedline;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Task() {}

    public Task(String title, String description, String dedline) {
        this.title = title;
        this.description = description;
        this.status = "pending";
        this.dedline = dedline;
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

    public String getDedline() { return dedline; }
    public void setDedline(String dedline) { this.dedline = dedline; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
