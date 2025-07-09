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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;

@Service
public class HealthDocumentService {

    @Autowired
    private HealthDocumentRepository healthDocumentRepository;

    // Lấy tất cả tài liệu có phân trang
    public Page<HealthDocumentDTO> getAllDocuments(int page, int size) {
        // Lấy tất cả documents với tags
        List<HealthDocument> allDocuments = healthDocumentRepository.findByIsPublishedTrueWithTags();
        
        // Sắp xếp theo createdAt giảm dần
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        // Xử lý pagination thủ công
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        // Tạo Page object
        Pageable pageable = PageRequest.of(page, size);
        Page<HealthDocumentDTO> result = new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
        
        return result;
    }

    // Lấy tài liệu theo ID
    public Optional<HealthDocumentDTO> getDocumentById(Long id) {
        HealthDocument document = healthDocumentRepository.findByIdWithTags(id);
        if (document != null && document.getIsPublished()) {
            // Tăng lượt xem
            document.setViewCount(document.getViewCount() + 1);
            healthDocumentRepository.save(document);
            return Optional.of(new HealthDocumentDTO(document));
        }
        return Optional.empty();
    }

    // Tìm kiếm tài liệu theo từ khóa
    public Page<HealthDocumentDTO> searchDocuments(String keyword, int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.searchByKeywordWithTags(keyword);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu theo danh mục
    public Page<HealthDocumentDTO> getDocumentsByCategory(HealthDocument.DocumentCategory category, int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByCategoryAndIsPublishedTrueWithTags(category);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu theo loại
    public Page<HealthDocumentDTO> getDocumentsByType(HealthDocument.DocumentType type, int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByTypeAndIsPublishedTrueWithTags(type);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu theo tác giả
    public Page<HealthDocumentDTO> getDocumentsByAuthor(String author, int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByAuthorContainingIgnoreCaseAndIsPublishedTrueWithTags(author);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu theo tag
    public Page<HealthDocumentDTO> getDocumentsByTag(String tag, int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByTagContainingWithTags(tag);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu nổi bật (theo lượt xem)
    public Page<HealthDocumentDTO> getPopularDocuments(int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByIsPublishedTrueWithTags();
        allDocuments.sort((a, b) -> b.getViewCount().compareTo(a.getViewCount()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu có file đính kèm
    public Page<HealthDocumentDTO> getDocumentsWithFiles(int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByFileUrlIsNotNullAndIsPublishedTrueWithTags();
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
    }

    // Lấy tài liệu theo danh mục và loại
    public Page<HealthDocumentDTO> getDocumentsByCategoryAndType(
            HealthDocument.DocumentCategory category, 
            HealthDocument.DocumentType type, 
            int page, int size) {
        List<HealthDocument> allDocuments = healthDocumentRepository.findByCategoryAndTypeAndIsPublishedTrueWithTags(category, type);
        allDocuments.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        
        int start = page * size;
        int end = Math.min(start + size, allDocuments.size());
        List<HealthDocument> pageContent = allDocuments.subList(start, end);
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(
            pageContent.stream().map(HealthDocumentDTO::new).collect(Collectors.toList()),
            pageable,
            allDocuments.size()
        );
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
        try {
            System.out.println("Service: Creating document with title: " + documentDTO.getTitle());
            
            HealthDocument document = new HealthDocument();
            document.setTitle(documentDTO.getTitle());
            document.setContent(documentDTO.getContent());
            document.setSummary(documentDTO.getSummary());
            document.setAuthor(documentDTO.getAuthor());
            document.setSource(documentDTO.getSource());
            document.setFileUrl(documentDTO.getFileUrl());
            document.setFileType(documentDTO.getFileType());
            
            // Xử lý tags
            if (documentDTO.getTags() != null) {
                document.setTags(documentDTO.getTags());
            } else {
                document.setTags(new ArrayList<>());
            }
            
            document.setIsPublished(documentDTO.getIsPublished() != null ? documentDTO.getIsPublished() : true);

            if (documentDTO.getCategory() != null) {
                System.out.println("Service: Setting category: " + documentDTO.getCategory());
                document.setCategory(HealthDocument.DocumentCategory.valueOf(documentDTO.getCategory()));
            }
            if (documentDTO.getType() != null) {
                System.out.println("Service: Setting type: " + documentDTO.getType());
                document.setType(HealthDocument.DocumentType.valueOf(documentDTO.getType()));
            }

            System.out.println("Service: Saving document to database...");
            HealthDocument savedDocument = healthDocumentRepository.save(document);
            System.out.println("Service: Document saved with ID: " + savedDocument.getId());
            
            return new HealthDocumentDTO(savedDocument);
        } catch (Exception e) {
            System.err.println("Service: Error creating document: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
            
            // Xử lý tags
            if (documentDTO.getTags() != null) {
                document.setTags(documentDTO.getTags());
            } else {
                document.setTags(new ArrayList<>());
            }
            
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