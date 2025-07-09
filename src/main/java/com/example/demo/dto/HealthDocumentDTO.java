package com.example.demo.dto;

import com.example.demo.model.HealthDocument;
import java.time.LocalDateTime;
import java.util.List;

public class HealthDocumentDTO {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String category;
    private String categoryDisplayName;
    private String type;
    private String typeDisplayName;
    private String author;
    private String source;
    private String fileUrl;
    private String fileType;
    private Integer viewCount;
    private Boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;

    // Constructors
    public HealthDocumentDTO() {}

    public HealthDocumentDTO(HealthDocument document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.content = document.getContent();
        this.summary = document.getSummary();
        this.category = document.getCategory() != null ? document.getCategory().name() : null;
        this.categoryDisplayName = document.getCategory() != null ? document.getCategory().getDisplayName() : null;
        this.type = document.getType() != null ? document.getType().name() : null;
        this.typeDisplayName = document.getType() != null ? document.getType().getDisplayName() : null;
        this.author = document.getAuthor();
        this.source = document.getSource();
        this.fileUrl = document.getFileUrl();
        this.fileType = document.getFileType();
        this.viewCount = document.getViewCount();
        this.isPublished = document.getIsPublished();
        this.createdAt = document.getCreatedAt();
        this.updatedAt = document.getUpdatedAt();
        this.tags = document.getTags();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDisplayName() {
        return typeDisplayName;
    }

    public void setTypeDisplayName(String typeDisplayName) {
        this.typeDisplayName = typeDisplayName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
} 