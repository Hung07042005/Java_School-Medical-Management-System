package com.example.demo.service;

import com.example.demo.dto.HealthBlogDTO;
import com.example.demo.model.HealthBlog;
import com.example.demo.repository.HealthBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HealthBlogService {

    @Autowired
    private HealthBlogRepository healthBlogRepository;

    // Lấy tất cả blog có phân trang
    public Page<HealthBlogDTO> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByIsPublishedTrue(pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo ID
    public Optional<HealthBlogDTO> getBlogById(Long id) {
        HealthBlog blog = healthBlogRepository.findByIdWithTags(id);
        if (blog != null && blog.getIsPublished()) {
            // Tăng lượt xem
            blog.setViewCount(blog.getViewCount() + 1);
            healthBlogRepository.save(blog);
            return Optional.of(new HealthBlogDTO(blog));
        }
        return Optional.empty();
    }

    // Tìm kiếm blog theo từ khóa
    public Page<HealthBlogDTO> searchBlogs(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.searchByKeyword(keyword, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo danh mục
    public Page<HealthBlogDTO> getBlogsByCategory(HealthBlog.BlogCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByCategoryAndIsPublishedTrue(category, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo tác giả
    public Page<HealthBlogDTO> getBlogsByAuthor(String author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByAuthorContainingIgnoreCaseAndIsPublishedTrue(author, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo tag
    public Page<HealthBlogDTO> getBlogsByTag(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByTagContaining(tag, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog nổi bật
    public Page<HealthBlogDTO> getFeaturedBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByIsFeaturedTrueAndIsPublishedTrue(pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog phổ biến (theo lượt xem)
    public Page<HealthBlogDTO> getPopularBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByIsPublishedTrue(pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo lượt thích
    public Page<HealthBlogDTO> getMostLikedBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("likeCount").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByIsPublishedTrue(pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog trong tháng này
    public Page<HealthBlogDTO> getBlogsThisMonth(int page, int size) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByPublishedAtBetweenAndIsPublishedTrue(startOfMonth, LocalDateTime.now(), pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog phổ biến trong tháng
    public Page<HealthBlogDTO> getPopularBlogsThisMonth(int page, int size) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findPopularThisMonth(startOfMonth, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog có hình ảnh
    public Page<HealthBlogDTO> getBlogsWithImages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByImageUrlIsNotNullAndIsPublishedTrue(pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy blog theo thời gian đọc
    public Page<HealthBlogDTO> getBlogsByReadingTime(String readingTime, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<HealthBlog> blogs = healthBlogRepository.findByReadingTime(readingTime, pageable);
        return blogs.map(HealthBlogDTO::new);
    }

    // Lấy tất cả danh mục blog
    public List<HealthBlog.BlogCategory> getAllCategories() {
        return java.util.Arrays.asList(HealthBlog.BlogCategory.values());
    }

    // Lấy tất cả tác giả
    public List<String> getAllAuthors() {
        return healthBlogRepository.findAllAuthors();
    }

    // Tạo blog mới
    public HealthBlogDTO createBlog(HealthBlogDTO blogDTO) {
        HealthBlog blog = new HealthBlog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setSummary(blogDTO.getSummary());
        blog.setAuthor(blogDTO.getAuthor());
        blog.setImageUrl(blogDTO.getImageUrl());
        blog.setTags(blogDTO.getTags());
        blog.setReadingTime(blogDTO.getReadingTime());
        blog.setSeoTitle(blogDTO.getSeoTitle());
        blog.setSeoDescription(blogDTO.getSeoDescription());
        blog.setIsPublished(blogDTO.getIsPublished() != null ? blogDTO.getIsPublished() : true);
        blog.setIsFeatured(blogDTO.getIsFeatured() != null ? blogDTO.getIsFeatured() : false);

        if (blogDTO.getCategory() != null) {
            blog.setCategory(HealthBlog.BlogCategory.valueOf(blogDTO.getCategory()));
        }

        HealthBlog savedBlog = healthBlogRepository.save(blog);
        return new HealthBlogDTO(savedBlog);
    }

    // Cập nhật blog
    public Optional<HealthBlogDTO> updateBlog(Long id, HealthBlogDTO blogDTO) {
        Optional<HealthBlog> existingBlog = healthBlogRepository.findById(id);
        if (existingBlog.isPresent()) {
            HealthBlog blog = existingBlog.get();
            blog.setTitle(blogDTO.getTitle());
            blog.setContent(blogDTO.getContent());
            blog.setSummary(blogDTO.getSummary());
            blog.setAuthor(blogDTO.getAuthor());
            blog.setImageUrl(blogDTO.getImageUrl());
            blog.setTags(blogDTO.getTags());
            blog.setReadingTime(blogDTO.getReadingTime());
            blog.setSeoTitle(blogDTO.getSeoTitle());
            blog.setSeoDescription(blogDTO.getSeoDescription());
            blog.setIsPublished(blogDTO.getIsPublished());
            blog.setIsFeatured(blogDTO.getIsFeatured());

            if (blogDTO.getCategory() != null) {
                blog.setCategory(HealthBlog.BlogCategory.valueOf(blogDTO.getCategory()));
            }

            HealthBlog updatedBlog = healthBlogRepository.save(blog);
            return Optional.of(new HealthBlogDTO(updatedBlog));
        }
        return Optional.empty();
    }

    // Xóa blog (soft delete)
    public boolean deleteBlog(Long id) {
        Optional<HealthBlog> blog = healthBlogRepository.findById(id);
        if (blog.isPresent()) {
            HealthBlog b = blog.get();
            b.setIsPublished(false);
            healthBlogRepository.save(b);
            return true;
        }
        return false;
    }

    // Tăng lượt thích
    public boolean likeBlog(Long id) {
        Optional<HealthBlog> blog = healthBlogRepository.findById(id);
        if (blog.isPresent()) {
            HealthBlog b = blog.get();
            b.setLikeCount(b.getLikeCount() + 1);
            healthBlogRepository.save(b);
            return true;
        }
        return false;
    }

    // Đếm số blog theo danh mục
    public long countBlogsByCategory(HealthBlog.BlogCategory category) {
        return healthBlogRepository.countByCategoryAndIsPublishedTrue(category);
    }

    // Lấy thống kê tổng quan
    public BlogStatistics getBlogStatistics() {
        List<HealthBlog.BlogCategory> categories = getAllCategories();
        List<String> authors = getAllAuthors();
        
        long totalBlogs = healthBlogRepository.count();
        long publishedBlogs = healthBlogRepository.findAll().stream()
                .filter(HealthBlog::getIsPublished)
                .count();
        long featuredBlogs = healthBlogRepository.findAll().stream()
                .filter(HealthBlog::getIsFeatured)
                .filter(HealthBlog::getIsPublished)
                .count();

        return new BlogStatistics(totalBlogs, publishedBlogs, featuredBlogs, categories, authors);
    }

    // Inner class cho thống kê
    public static class BlogStatistics {
        private long totalBlogs;
        private long publishedBlogs;
        private long featuredBlogs;
        private List<HealthBlog.BlogCategory> categories;
        private List<String> authors;

        public BlogStatistics(long totalBlogs, long publishedBlogs, long featuredBlogs,
                            List<HealthBlog.BlogCategory> categories, List<String> authors) {
            this.totalBlogs = totalBlogs;
            this.publishedBlogs = publishedBlogs;
            this.featuredBlogs = featuredBlogs;
            this.categories = categories;
            this.authors = authors;
        }

        // Getters
        public long getTotalBlogs() { return totalBlogs; }
        public long getPublishedBlogs() { return publishedBlogs; }
        public long getFeaturedBlogs() { return featuredBlogs; }
        public List<HealthBlog.BlogCategory> getCategories() { return categories; }
        public List<String> getAuthors() { return authors; }
    }
} 