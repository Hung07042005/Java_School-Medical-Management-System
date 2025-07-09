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

    // Tìm tài liệu theo danh mục - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.category = :category AND hd.isPublished = true")
    List<HealthDocument> findByCategoryAndIsPublishedTrueWithTags(@Param("category") HealthDocument.DocumentCategory category);

    // Tìm tài liệu theo loại - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.type = :type AND hd.isPublished = true")
    List<HealthDocument> findByTypeAndIsPublishedTrueWithTags(@Param("type") HealthDocument.DocumentType type);

    // Tìm tài liệu theo tác giả - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE LOWER(hd.author) LIKE LOWER(CONCAT('%', :author, '%')) AND hd.isPublished = true")
    List<HealthDocument> findByAuthorContainingIgnoreCaseAndIsPublishedTrueWithTags(@Param("author") String author);

    // Tìm tài liệu theo tiêu đề (tìm kiếm mờ) - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE LOWER(hd.title) LIKE LOWER(CONCAT('%', :title, '%')) AND hd.isPublished = true")
    List<HealthDocument> findByTitleContainingIgnoreCaseAndIsPublishedTrueWithTags(@Param("title") String title);

    // Tìm tài liệu theo tag - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags t WHERE t LIKE %:tag% AND hd.isPublished = true")
    List<HealthDocument> findByTagContainingWithTags(@Param("tag") String tag);

    // Tìm tài liệu đã xuất bản (sắp xếp động) - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.isPublished = true")
    List<HealthDocument> findByIsPublishedTrueWithTags();
    
    // Tìm tài liệu đã xuất bản (sắp xếp động) - không fetch tags
    Page<HealthDocument> findByIsPublishedTrue(Pageable pageable);

    // Tìm kiếm tổng hợp - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.isPublished = true AND " +
           "(LOWER(hd.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    List<HealthDocument> searchByKeywordWithTags(@Param("keyword") String keyword);
    
    // Tìm kiếm tổng hợp - không fetch tags
    @Query("SELECT hd FROM HealthDocument hd WHERE hd.isPublished = true AND " +
           "(LOWER(hd.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hd.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))" )
    Page<HealthDocument> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Đếm số tài liệu theo danh mục
    long countByCategoryAndIsPublishedTrue(HealthDocument.DocumentCategory category);

    // Lấy tài liệu theo danh mục và loại - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.category = :category AND hd.type = :type AND hd.isPublished = true")
    List<HealthDocument> findByCategoryAndTypeAndIsPublishedTrueWithTags(
            @Param("category") HealthDocument.DocumentCategory category, 
            @Param("type") HealthDocument.DocumentType type);

    // Tìm tài liệu có file đính kèm - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.fileUrl IS NOT NULL AND hd.isPublished = true")
    List<HealthDocument> findByFileUrlIsNotNullAndIsPublishedTrueWithTags();

    // Lấy tất cả danh mục có tài liệu
    @Query("SELECT DISTINCT hd.category FROM HealthDocument hd WHERE hd.isPublished = true")
    List<HealthDocument.DocumentCategory> findAllCategories();

    // Lấy tất cả loại tài liệu có sẵn
    @Query("SELECT DISTINCT hd.type FROM HealthDocument hd WHERE hd.isPublished = true")
    List<HealthDocument.DocumentType> findAllTypes();
    
    // Tìm tài liệu theo ID với tags - fetch tags eagerly
    @Query("SELECT hd FROM HealthDocument hd LEFT JOIN FETCH hd.tags WHERE hd.id = :id")
    HealthDocument findByIdWithTags(@Param("id") Long id);
} 