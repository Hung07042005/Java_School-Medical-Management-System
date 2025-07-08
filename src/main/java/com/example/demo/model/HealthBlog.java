package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "health_blogs")
public class HealthBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Tiêu đề bài viết

    @Column(columnDefinition = "TEXT")
    private String content; // Nội dung bài viết

    @Column(length = 500)
    private String summary; // Tóm tắt bài viết

    @Column(length = 200)
    private String author; // Tác giả

    @Enumerated(EnumType.STRING)
    private BlogCategory category; // Danh mục blog

    @Column(length = 200)
    private String imageUrl; // URL hình ảnh đại diện

    private Integer viewCount = 0; // Số lượt xem

    private Integer likeCount = 0; // Số lượt thích

    private Boolean isPublished = true; // Trạng thái xuất bản

    private Boolean isFeatured = false; // Bài viết nổi bật

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt; // Thời gian xuất bản

    @ElementCollection
    @CollectionTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "tag")
    private List<String> tags; // Các tag liên quan

    @Column(length = 100)
    private String readingTime; // Thời gian đọc ước tính

    @Column(length = 200)
    private String seoTitle; // Tiêu đề SEO

    @Column(length = 500)
    private String seoDescription; // Mô tả SEO

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isPublished && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (isPublished && publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    // Enum cho danh mục blog
    public enum BlogCategory {
        NUTRITION_TIPS("Mẹo dinh dưỡng"),
        HEALTHY_LIFESTYLE("Lối sống lành mạnh"),
        MENTAL_HEALTH("Sức khỏe tâm thần"),
        PHYSICAL_ACTIVITY("Hoạt động thể chất"),
        DISEASE_PREVENTION("Phòng bệnh"),
        FIRST_AID_TIPS("Mẹo sơ cứu"),
        PARENTING_HEALTH("Sức khỏe cho phụ huynh"),
        TEACHER_HEALTH("Sức khỏe cho giáo viên"),
        SEASONAL_HEALTH("Sức khỏe theo mùa"),
        SUCCESS_STORIES("Câu chuyện thành công"),
        EXPERT_ADVICE("Lời khuyên chuyên gia");

        private final String displayName;

        BlogCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public HealthBlog() {}

    public HealthBlog(String title, String content, String summary, String author, BlogCategory category) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.author = author;
        this.category = category;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BlogCategory getCategory() {
        return category;
    }

    public void setCategory(BlogCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
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

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }
} 