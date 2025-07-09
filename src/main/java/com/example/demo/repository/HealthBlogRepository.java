package com.example.demo.repository;

import com.example.demo.model.HealthBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthBlogRepository extends JpaRepository<HealthBlog, Long> {

    // Tìm blog theo danh mục
    Page<HealthBlog> findByCategoryAndIsPublishedTrue(HealthBlog.BlogCategory category, Pageable pageable);

    // Tìm blog theo tác giả
    Page<HealthBlog> findByAuthorContainingIgnoreCaseAndIsPublishedTrue(String author, Pageable pageable);

    // Tìm blog theo tiêu đề (tìm kiếm mờ)
    Page<HealthBlog> findByTitleContainingIgnoreCaseAndIsPublishedTrue(String title, Pageable pageable);

    // Tìm blog theo tag
    @Query("SELECT hb FROM HealthBlog hb JOIN hb.tags t WHERE t LIKE %:tag% AND hb.isPublished = true")
    Page<HealthBlog> findByTagContaining(@Param("tag") String tag, Pageable pageable);

    // Tìm blog nổi bật
    Page<HealthBlog> findByIsFeaturedTrueAndIsPublishedTrue(Pageable pageable);

    // Tìm blog đã xuất bản (sắp xếp động)
    Page<HealthBlog> findByIsPublishedTrue(Pageable pageable);

    // Tìm blog trong khoảng thời gian
    Page<HealthBlog> findByPublishedAtBetweenAndIsPublishedTrue(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Tìm kiếm tổng hợp
    @Query("SELECT hb FROM HealthBlog hb WHERE hb.isPublished = true AND " +
           "(LOWER(hb.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hb.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(hb.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<HealthBlog> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Đếm số blog theo danh mục
    long countByCategoryAndIsPublishedTrue(HealthBlog.BlogCategory category);

    // Lấy blog theo tác giả và danh mục
    Page<HealthBlog> findByAuthorAndCategoryAndIsPublishedTrue(
            String author, HealthBlog.BlogCategory category, Pageable pageable);

    // Tìm blog có hình ảnh
    Page<HealthBlog> findByImageUrlIsNotNullAndIsPublishedTrue(Pageable pageable);

    // Lấy tất cả danh mục blog có sẵn
    @Query("SELECT DISTINCT hb.category FROM HealthBlog hb WHERE hb.isPublished = true")
    List<HealthBlog.BlogCategory> findAllCategories();

    // Lấy tất cả tác giả có blog
    @Query("SELECT DISTINCT hb.author FROM HealthBlog hb WHERE hb.isPublished = true")
    List<String> findAllAuthors();

    // Tìm blog theo thời gian đọc
    @Query("SELECT hb FROM HealthBlog hb WHERE hb.isPublished = true AND hb.readingTime = :readingTime")
    Page<HealthBlog> findByReadingTime(@Param("readingTime") String readingTime, Pageable pageable);

    // Tìm blog phổ biến trong tháng
    @Query("SELECT hb FROM HealthBlog hb WHERE hb.isPublished = true AND " +
           "hb.publishedAt >= :startOfMonth ORDER BY hb.viewCount DESC")
    Page<HealthBlog> findPopularThisMonth(@Param("startOfMonth") LocalDateTime startOfMonth, Pageable pageable);

    // Tăng lượt xem
    @Query("UPDATE HealthBlog hb SET hb.viewCount = hb.viewCount + 1 WHERE hb.id = :id")
    void incrementViewCount(@Param("id") Long id);

    // Tăng lượt thích
    @Query("UPDATE HealthBlog hb SET hb.likeCount = hb.likeCount + 1 WHERE hb.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Query("SELECT hb FROM HealthBlog hb LEFT JOIN FETCH hb.tags WHERE hb.id = :id")
    HealthBlog findByIdWithTags(@Param("id") Long id);
} 