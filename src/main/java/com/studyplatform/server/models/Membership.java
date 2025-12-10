package com.studyplatform.server.models;

import jakarta.persistence.*;

@Entity
@Table(name = "membrerships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "group_id", nullable = false)
    private Long groupId;
    @Column(nullable = false)
    private String role;
    @Column(name = "joined_at", nullable = false)
    private String joinedAt;

    public Membership() { }

    public Membership(Long userId, Long groupId, String role, String joinedAt) {
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJoinedAt() { return joinedAt; }
    public void setJoinedAt(String joinedAt) { this.joinedAt = joinedAt; }
}
