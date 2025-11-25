package com.studyplatform.server.models;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;
    @Column(name = "path_or_url", nullable = false)
    private String pathOrUrl;
    @Column(name = "uploaded_at", nullable = false)
    private String uploadedAt;

    public Resource() { }

    public Resource(String title, String type, String pathOrUrl, String uploadedAt) {
        this.title = title;
        this.type = type;
        this.pathOrUrl = pathOrUrl;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPathOrUrl() { return pathOrUrl; }
    public void setPathOrUrl(String pathOrUrl) { this.pathOrUrl = pathOrUrl; }

    public String getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(String uploadedAt) { this.uploadedAt = uploadedAt; }
}
