package com.example.demo.repository;

import com.example.demo.model.HealthDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthDocumentRepository extends JpaRepository<HealthDocument, Long> {

    // Tìm tài liệu theo danh mục
    Page<HealthDocument> findByCategoryAndIsPublishedTrue(HealthDocument.DocumentCategory category, Pageable pageable);

    // Tìm tài liệu theo loại
    Page<HealthDocument> findByTypeAndIsPublishedTrue(HealthDocument.DocumentType type, Pageable pageable);

    // Tìm tài liệu theo tác giả
    Page<HealthDocument> findByAuthorContainingIgnoreCaseAndIsPublishedTrue(String author, Pageable pageable);

    // Tìm tài liệu theo tiêu đề (tìm kiếm mờ)
    Page<HealthDocument> findByTitleContainingIgnoreCaseAndIsPublishedTrue(String title, Pageable pageable);

    // Tìm tài liệu theo tag
    @Query("SELECT hd FROM HealthDocument hd JOIN hd.tags t WHERE t LIKE %:tag% AND hd.isPublished = true")
    Page<HealthDocument> findByTagContaining(@Param("tag") String tag, Pageable pageable);

    // Tìm tài liệu đã xuất bản (sắp xếp động)
    Page<HealthDocument> findByIsPublishedTrue(Pageable pageable);

    // Tìm kiếm tổng hợp
    @Query("SELECT hd FROM HealthDocument hd WHERE hd.isPublished = true AND " +
           "(LOWER(hd.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    Page<HealthDocument> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Đếm số tài liệu theo danh mục
    long countByCategoryAndIsPublishedTrue(HealthDocument.DocumentCategory category);

    // Lấy tài liệu theo danh mục và loại
    Page<HealthDocument> findByCategoryAndTypeAndIsPublishedTrue(
            HealthDocument.DocumentCategory category, 
            HealthDocument.DocumentType type, 
            Pageable pageable);

    // Tìm tài liệu có file đính kèm
    Page<HealthDocument> findByFileUrlIsNotNullAndIsPublishedTrue(Pageable pageable);

    // Lấy tất cả danh mục có tài liệu
    @Query("SELECT DISTINCT hd.category FROM HealthDocument hd WHERE hd.isPublished = true")
    List<HealthDocument.DocumentCategory> findAllCategories();

    // Lấy tất cả loại tài liệu có sẵn
    @Query("SELECT DISTINCT hd.type FROM HealthDocument hd WHERE hd.isPublished = true")
    List<HealthDocument.DocumentType> findAllTypes();
} 