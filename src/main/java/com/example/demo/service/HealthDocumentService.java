package com.example.demo.service;

import com.example.demo.dto.HealthDocumentDTO;
import com.example.demo.model.HealthDocument;
import com.example.demo.repository.HealthDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HealthDocumentService {

    @Autowired
    private HealthDocumentRepository healthDocumentRepository;

    // Lấy tất cả tài liệu có phân trang
    public Page<HealthDocumentDTO> getAllDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByIsPublishedTrue(pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo ID
    public Optional<HealthDocumentDTO> getDocumentById(Long id) {
        Optional<HealthDocument> document = healthDocumentRepository.findById(id);
        if (document.isPresent() && document.get().getIsPublished()) {
            // Tăng lượt xem
            HealthDocument doc = document.get();
            doc.setViewCount(doc.getViewCount() + 1);
            healthDocumentRepository.save(doc);
            return Optional.of(new HealthDocumentDTO(doc));
        }
        return Optional.empty();
    }

    // Tìm kiếm tài liệu theo từ khóa
    public Page<HealthDocumentDTO> searchDocuments(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.searchByKeyword(keyword, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo danh mục
    public Page<HealthDocumentDTO> getDocumentsByCategory(HealthDocument.DocumentCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByCategoryAndIsPublishedTrue(category, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo loại
    public Page<HealthDocumentDTO> getDocumentsByType(HealthDocument.DocumentType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByTypeAndIsPublishedTrue(type, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo tác giả
    public Page<HealthDocumentDTO> getDocumentsByAuthor(String author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByAuthorContainingIgnoreCaseAndIsPublishedTrue(author, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo tag
    public Page<HealthDocumentDTO> getDocumentsByTag(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByTagContaining(tag, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu nổi bật (theo lượt xem)
    public Page<HealthDocumentDTO> getPopularDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByIsPublishedTrue(pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu có file đính kèm
    public Page<HealthDocumentDTO> getDocumentsWithFiles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByFileUrlIsNotNullAndIsPublishedTrue(pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tài liệu theo danh mục và loại
    public Page<HealthDocumentDTO> getDocumentsByCategoryAndType(
            HealthDocument.DocumentCategory category, 
            HealthDocument.DocumentType type, 
            int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<HealthDocument> documents = healthDocumentRepository.findByCategoryAndTypeAndIsPublishedTrue(category, type, pageable);
        return documents.map(HealthDocumentDTO::new);
    }

    // Lấy tất cả danh mục
    public List<HealthDocument.DocumentCategory> getAllCategories() {
        return healthDocumentRepository.findAllCategories();
    }

    // Lấy tất cả loại tài liệu
    public List<HealthDocument.DocumentType> getAllTypes() {
        return healthDocumentRepository.findAllTypes();
    }

    // Tạo tài liệu mới
    public HealthDocumentDTO createDocument(HealthDocumentDTO documentDTO) {
        HealthDocument document = new HealthDocument();
        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        document.setSummary(documentDTO.getSummary());
        document.setAuthor(documentDTO.getAuthor());
        document.setSource(documentDTO.getSource());
        document.setFileUrl(documentDTO.getFileUrl());
        document.setFileType(documentDTO.getFileType());
        document.setTags(documentDTO.getTags());
        document.setIsPublished(documentDTO.getIsPublished() != null ? documentDTO.getIsPublished() : true);

        if (documentDTO.getCategory() != null) {
            document.setCategory(HealthDocument.DocumentCategory.valueOf(documentDTO.getCategory()));
        }
        if (documentDTO.getType() != null) {
            document.setType(HealthDocument.DocumentType.valueOf(documentDTO.getType()));
        }

        HealthDocument savedDocument = healthDocumentRepository.save(document);
        return new HealthDocumentDTO(savedDocument);
    }

    // Cập nhật tài liệu
    public Optional<HealthDocumentDTO> updateDocument(Long id, HealthDocumentDTO documentDTO) {
        Optional<HealthDocument> existingDocument = healthDocumentRepository.findById(id);
        if (existingDocument.isPresent()) {
            HealthDocument document = existingDocument.get();
            document.setTitle(documentDTO.getTitle());
            document.setContent(documentDTO.getContent());
            document.setSummary(documentDTO.getSummary());
            document.setAuthor(documentDTO.getAuthor());
            document.setSource(documentDTO.getSource());
            document.setFileUrl(documentDTO.getFileUrl());
            document.setFileType(documentDTO.getFileType());
            document.setTags(documentDTO.getTags());
            document.setIsPublished(documentDTO.getIsPublished());

            if (documentDTO.getCategory() != null) {
                document.setCategory(HealthDocument.DocumentCategory.valueOf(documentDTO.getCategory()));
            }
            if (documentDTO.getType() != null) {
                document.setType(HealthDocument.DocumentType.valueOf(documentDTO.getType()));
            }

            HealthDocument updatedDocument = healthDocumentRepository.save(document);
            return Optional.of(new HealthDocumentDTO(updatedDocument));
        }
        return Optional.empty();
    }

    // Xóa tài liệu (soft delete)
    public boolean deleteDocument(Long id) {
        Optional<HealthDocument> document = healthDocumentRepository.findById(id);
        if (document.isPresent()) {
            HealthDocument doc = document.get();
            doc.setIsPublished(false);
            healthDocumentRepository.save(doc);
            return true;
        }
        return false;
    }

    // Đếm số tài liệu theo danh mục
    public long countDocumentsByCategory(HealthDocument.DocumentCategory category) {
        return healthDocumentRepository.countByCategoryAndIsPublishedTrue(category);
    }

    // Lấy thống kê tổng quan
    public DocumentStatistics getDocumentStatistics() {
        List<HealthDocument.DocumentCategory> categories = getAllCategories();
        List<HealthDocument.DocumentType> types = getAllTypes();
        
        long totalDocuments = healthDocumentRepository.count();
        long publishedDocuments = healthDocumentRepository.findAll().stream()
                .filter(HealthDocument::getIsPublished)
                .count();

        return new DocumentStatistics(totalDocuments, publishedDocuments, categories, types);
    }

    // Inner class cho thống kê
    public static class DocumentStatistics {
        private long totalDocuments;
        private long publishedDocuments;
        private List<HealthDocument.DocumentCategory> categories;
        private List<HealthDocument.DocumentType> types;

        public DocumentStatistics(long totalDocuments, long publishedDocuments, 
                                List<HealthDocument.DocumentCategory> categories, 
                                List<HealthDocument.DocumentType> types) {
            this.totalDocuments = totalDocuments;
            this.publishedDocuments = publishedDocuments;
            this.categories = categories;
            this.types = types;
        }

        // Getters
        public long getTotalDocuments() { return totalDocuments; }
        public long getPublishedDocuments() { return publishedDocuments; }
        public List<HealthDocument.DocumentCategory> getCategories() { return categories; }
        public List<HealthDocument.DocumentType> getTypes() { return types; }
    }
} 