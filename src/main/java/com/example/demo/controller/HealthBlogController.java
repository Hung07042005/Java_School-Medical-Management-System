package com.example.demo.controller;

import com.example.demo.dto.HealthBlogDTO;
import com.example.demo.model.HealthBlog;
import com.example.demo.service.HealthBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/health-blogs")
public class HealthBlogController {

    @Autowired
    private HealthBlogService healthBlogService;

    // Trang danh sách blog
    @GetMapping
    public String listBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model) {

        Page<HealthBlogDTO> blogs;
        
        if (search != null && !search.trim().isEmpty()) {
            blogs = healthBlogService.searchBlogs(search, page, size);
            model.addAttribute("search", search);
        } else if (category != null && !category.trim().isEmpty()) {
            HealthBlog.BlogCategory blogCategory = HealthBlog.BlogCategory.valueOf(category);
            blogs = healthBlogService.getBlogsByCategory(blogCategory, page, size);
            model.addAttribute("selectedCategory", category);
        } else {
            blogs = healthBlogService.getAllBlogs(page, size);
        }

        model.addAttribute("blogs", blogs);
        model.addAttribute("categories", healthBlogService.getAllCategories());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());

        return "health-blogs/blog-list";
    }

    // Trang chi tiết blog
    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id, Model model) {
        Optional<HealthBlogDTO> blog = healthBlogService.getBlogById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            
            // Lấy blog liên quan
            Page<HealthBlogDTO> relatedBlogs = healthBlogService.getBlogsByCategory(
                HealthBlog.BlogCategory.valueOf(blog.get().getCategory()), 0, 5);
            model.addAttribute("relatedBlogs", relatedBlogs.getContent());
            
            return "health-blogs/blog-detail";
        }
        return "redirect:/health-blogs?error=not-found";
    }

    // Trang tạo blog mới
    @GetMapping("/create")
    public String createBlogForm(Model model) {
        model.addAttribute("blog", new HealthBlogDTO());
        model.addAttribute("categories", healthBlogService.getAllCategories());
        return "health-blogs/blog-form";
    }

    // Xử lý tạo blog mới
    @PostMapping("/create")
    public String createBlog(@ModelAttribute HealthBlogDTO blog, RedirectAttributes redirectAttributes) {
        try {
            HealthBlogDTO savedBlog = healthBlogService.createBlog(blog);
            redirectAttributes.addFlashAttribute("success", "Bài viết đã được tạo thành công!");
            return "redirect:/health-blogs/" + savedBlog.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tạo bài viết: " + e.getMessage());
            return "redirect:/health-blogs/create";
        }
    }

    // Trang chỉnh sửa blog
    @GetMapping("/{id}/edit")
    public String editBlogForm(@PathVariable Long id, Model model) {
        Optional<HealthBlogDTO> blog = healthBlogService.getBlogById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            model.addAttribute("categories", healthBlogService.getAllCategories());
            return "health-blogs/blog-form";
        }
        return "redirect:/health-blogs?error=not-found";
    }

    // Xử lý cập nhật blog
    @PostMapping("/{id}/edit")
    public String updateBlog(@PathVariable Long id, @ModelAttribute HealthBlogDTO blog, RedirectAttributes redirectAttributes) {
        try {
            Optional<HealthBlogDTO> updatedBlog = healthBlogService.updateBlog(id, blog);
            if (updatedBlog.isPresent()) {
                redirectAttributes.addFlashAttribute("success", "Bài viết đã được cập nhật thành công!");
                return "redirect:/health-blogs/" + id;
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết!");
                return "redirect:/health-blogs";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật bài viết: " + e.getMessage());
            return "redirect:/health-blogs/" + id + "/edit";
        }
    }

    // Xóa blog
    @PostMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = healthBlogService.deleteBlog(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Bài viết đã được xóa thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài viết!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa bài viết: " + e.getMessage());
        }
        return "redirect:/health-blogs";
    }

    // Trang blog nổi bật
    @GetMapping("/featured")
    public String featuredBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getFeaturedBlogs(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/featured-blogs";
    }

    // Trang blog phổ biến
    @GetMapping("/popular")
    public String popularBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getPopularBlogs(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/popular-blogs";
    }

    // Trang blog được thích nhiều nhất
    @GetMapping("/most-liked")
    public String mostLikedBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getMostLikedBlogs(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/most-liked-blogs";
    }

    // Trang blog trong tháng này
    @GetMapping("/this-month")
    public String blogsThisMonth(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getBlogsThisMonth(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/blogs-this-month";
    }

    // Trang blog phổ biến trong tháng
    @GetMapping("/popular-this-month")
    public String popularBlogsThisMonth(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getPopularBlogsThisMonth(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/popular-this-month";
    }

    // Trang blog có hình ảnh
    @GetMapping("/with-images")
    public String blogsWithImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getBlogsWithImages(page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/blogs-with-images";
    }

    // Trang thống kê blog
    @GetMapping("/statistics")
    public String blogStatistics(Model model) {
        HealthBlogService.BlogStatistics stats = healthBlogService.getBlogStatistics();
        model.addAttribute("statistics", stats);
        return "health-blogs/blog-statistics";
    }

    // Thích blog
    @PostMapping("/{id}/like")
    @ResponseBody
    public String likeBlog(@PathVariable Long id) {
        try {
            boolean liked = healthBlogService.likeBlog(id);
            if (liked) {
                return "success";
            } else {
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    // API endpoint để lấy blog theo tag
    @GetMapping("/tag/{tag}")
    public String blogsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getBlogsByTag(tag, page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("tag", tag);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/blogs-by-tag";
    }

    // API endpoint để lấy blog theo tác giả
    @GetMapping("/author/{author}")
    public String blogsByAuthor(
            @PathVariable String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getBlogsByAuthor(author, page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("author", author);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/blogs-by-author";
    }

    // API endpoint để lấy blog theo thời gian đọc
    @GetMapping("/reading-time/{readingTime}")
    public String blogsByReadingTime(
            @PathVariable String readingTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthBlogDTO> blogs = healthBlogService.getBlogsByReadingTime(readingTime, page, size);
        model.addAttribute("blogs", blogs);
        model.addAttribute("readingTime", readingTime);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogs.getTotalPages());
        model.addAttribute("totalItems", blogs.getTotalElements());
        
        return "health-blogs/blogs-by-reading-time";
    }
} 