package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "health_documents")
public class HealthDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String title; // Tiêu đề tài liệu

    @Column(columnDefinition = "NTEXT")
    private String content; // Nội dung tài liệu

    @Column(length = 500, columnDefinition = "NVARCHAR(500)")
    private String summary; // Tóm tắt ngắn gọn

    @Enumerated(EnumType.STRING)
    private DocumentCategory category; // Danh mục tài liệu

    @Enumerated(EnumType.STRING)
    private DocumentType type; // Loại tài liệu

    @Column(length = 100, columnDefinition = "NVARCHAR(100)")
    private String author; // Tác giả

    @Column(length = 200, columnDefinition = "NVARCHAR(200)")
    private String source; // Nguồn tài liệu

    @Column(length = 200, columnDefinition = "NVARCHAR(200)")
    private String fileUrl; // Đường dẫn file đính kèm (nếu có)

    @Column(length = 50, columnDefinition = "NVARCHAR(50)")
    private String fileType; // Loại file (PDF, DOC, etc.)

    private Integer viewCount = 0; // Số lượt xem

    private Boolean isPublished = true; // Trạng thái xuất bản

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "document_tags", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "tag")
    private List<String> tags; // Các tag liên quan

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enum cho danh mục tài liệu
    public enum DocumentCategory {
        NUTRITION("Dinh dưỡng"),
        HYGIENE("Vệ sinh"),
        FIRST_AID("Sơ cứu"),
        MENTAL_HEALTH("Sức khỏe tâm thần"),
        PHYSICAL_HEALTH("Sức khỏe thể chất"),
        DISEASE_PREVENTION("Phòng bệnh"),
        EMERGENCY("Khẩn cấp"),
        GENERAL("Chung");

        private final String displayName;

        DocumentCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Enum cho loại tài liệu
    public enum DocumentType {
        ARTICLE("Bài viết"),
        GUIDE("Hướng dẫn"),
        POLICY("Chính sách"),
        PROCEDURE("Quy trình"),
        CHECKLIST("Danh sách kiểm tra"),
        FORM("Mẫu biểu"),
        PRESENTATION("Thuyết trình");

        private final String displayName;

        DocumentType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public HealthDocument() {}

    public HealthDocument(String title, String content, String summary, DocumentCategory category, DocumentType type, String author) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.category = category;
        this.type = type;
        this.author = author;
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

    public DocumentCategory getCategory() {
        return category;
    }

    public void setCategory(DocumentCategory category) {
        this.category = category;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
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