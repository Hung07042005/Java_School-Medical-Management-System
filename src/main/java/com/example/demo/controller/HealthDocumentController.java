package com.example.demo.controller;

import com.example.demo.dto.HealthDocumentDTO;
import com.example.demo.model.HealthDocument;
import com.example.demo.service.HealthDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/health-documents")
public class HealthDocumentController {

    @Autowired
    private HealthDocumentService healthDocumentService;

    // Trang danh sách tài liệu
    @GetMapping
    public String listDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search,
            Model model) {

        Page<HealthDocumentDTO> documents;
        
        if (search != null && !search.trim().isEmpty()) {
            documents = healthDocumentService.searchDocuments(search, page, size);
            model.addAttribute("search", search);
        } else if (category != null && !category.trim().isEmpty()) {
            HealthDocument.DocumentCategory docCategory = HealthDocument.DocumentCategory.valueOf(category);
            documents = healthDocumentService.getDocumentsByCategory(docCategory, page, size);
            model.addAttribute("selectedCategory", category);
        } else if (type != null && !type.trim().isEmpty()) {
            HealthDocument.DocumentType docType = HealthDocument.DocumentType.valueOf(type);
            documents = healthDocumentService.getDocumentsByType(docType, page, size);
            model.addAttribute("selectedType", type);
        } else {
            documents = healthDocumentService.getAllDocuments(page, size);
        }

        model.addAttribute("documents", documents);
        model.addAttribute("categories", healthDocumentService.getAllCategories());
        model.addAttribute("types", healthDocumentService.getAllTypes());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalItems", documents.getTotalElements());

        return "health-documents/document-list";
    }

    // Trang chi tiết tài liệu
    @GetMapping("/{id}")
    public String viewDocument(@PathVariable Long id, Model model) {
        Optional<HealthDocumentDTO> document = healthDocumentService.getDocumentById(id);
        if (document.isPresent()) {
            model.addAttribute("document", document.get());
            
            // Lấy tài liệu liên quan
            Page<HealthDocumentDTO> relatedDocuments = healthDocumentService.getDocumentsByCategory(
                HealthDocument.DocumentCategory.valueOf(document.get().getCategory()), 0, 5);
            model.addAttribute("relatedDocuments", relatedDocuments.getContent());
            
            return "health-documents/document-detail";
        }
        return "redirect:/health-documents?error=not-found";
    }

    // Trang tạo tài liệu mới
    @GetMapping("/create")
    public String createDocumentForm(Model model) {
        model.addAttribute("document", new HealthDocumentDTO());
        model.addAttribute("categories", healthDocumentService.getAllCategories());
        model.addAttribute("types", healthDocumentService.getAllTypes());
        return "health-documents/document-form";
    }

    // Xử lý tạo tài liệu mới
    @PostMapping("/create")
    public String createDocument(@ModelAttribute HealthDocumentDTO document, 
                               @RequestParam(required = false) String tagsString,
                               RedirectAttributes redirectAttributes) {
        try {
            // Validation
            if (document.getTitle() == null || document.getTitle().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Tiêu đề không được để trống!");
                return "redirect:/health-documents/create";
            }
            
            if (document.getContent() == null || document.getContent().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Nội dung không được để trống!");
                return "redirect:/health-documents/create";
            }
            
            if (document.getAuthor() == null || document.getAuthor().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Tác giả không được để trống!");
                return "redirect:/health-documents/create";
            }
            
            if (document.getCategory() == null || document.getCategory().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Danh mục không được để trống!");
                return "redirect:/health-documents/create";
            }
            
            if (document.getType() == null || document.getType().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Loại tài liệu không được để trống!");
                return "redirect:/health-documents/create";
            }
            
            // Xử lý tags từ form
            if (tagsString != null && !tagsString.trim().isEmpty()) {
                String[] tagsArray = tagsString.split(",");
                List<String> tagsList = new ArrayList<>();
                for (String tag : tagsArray) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        tagsList.add(trimmedTag);
                    }
                }
                document.setTags(tagsList);
            }
            
            System.out.println("Creating document: " + document.getTitle());
            System.out.println("Category: " + document.getCategory());
            System.out.println("Type: " + document.getType());
            System.out.println("Author: " + document.getAuthor());
            
            HealthDocumentDTO savedDocument = healthDocumentService.createDocument(document);
            redirectAttributes.addFlashAttribute("success", "Tài liệu đã được tạo thành công!");
            return "redirect:/health-documents/" + savedDocument.getId();
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tạo tài liệu: " + e.getMessage());
            return "redirect:/health-documents/create";
        }
    }

    // Trang chỉnh sửa tài liệu
    @GetMapping("/{id}/edit")
    public String editDocumentForm(@PathVariable Long id, Model model) {
        Optional<HealthDocumentDTO> document = healthDocumentService.getDocumentById(id);
        if (document.isPresent()) {
            model.addAttribute("document", document.get());
            model.addAttribute("categories", healthDocumentService.getAllCategories());
            model.addAttribute("types", healthDocumentService.getAllTypes());
            return "health-documents/document-form";
        }
        return "redirect:/health-documents?error=not-found";
    }

    // Xử lý cập nhật tài liệu
    @PostMapping("/{id}/edit")
    public String updateDocument(@PathVariable Long id, 
                               @ModelAttribute HealthDocumentDTO document,
                               @RequestParam(required = false) String tagsString,
                               RedirectAttributes redirectAttributes) {
        try {
            // Xử lý tags từ form
            if (tagsString != null && !tagsString.trim().isEmpty()) {
                String[] tagsArray = tagsString.split(",");
                List<String> tagsList = new ArrayList<>();
                for (String tag : tagsArray) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        tagsList.add(trimmedTag);
                    }
                }
                document.setTags(tagsList);
            }
            
            Optional<HealthDocumentDTO> updatedDocument = healthDocumentService.updateDocument(id, document);
            if (updatedDocument.isPresent()) {
                redirectAttributes.addFlashAttribute("success", "Tài liệu đã được cập nhật thành công!");
                return "redirect:/health-documents/" + id;
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài liệu!");
                return "redirect:/health-documents";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật tài liệu: " + e.getMessage());
            return "redirect:/health-documents/" + id + "/edit";
        }
    }

    // Xóa tài liệu
    @PostMapping("/{id}/delete")
    public String deleteDocument(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = healthDocumentService.deleteDocument(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Tài liệu đã được xóa thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài liệu!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa tài liệu: " + e.getMessage());
        }
        return "redirect:/health-documents";
    }

    // Trang tài liệu phổ biến
    @GetMapping("/popular")
    public String popularDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthDocumentDTO> documents = healthDocumentService.getPopularDocuments(page, size);
        model.addAttribute("documents", documents);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalItems", documents.getTotalElements());
        
        return "health-documents/popular-documents";
    }

    // Trang tài liệu có file đính kèm
    @GetMapping("/with-files")
    public String documentsWithFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthDocumentDTO> documents = healthDocumentService.getDocumentsWithFiles(page, size);
        model.addAttribute("documents", documents);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalItems", documents.getTotalElements());
        
        return "health-documents/documents-with-files";
    }

    // Trang thống kê tài liệu
    @GetMapping("/statistics")
    public String documentStatistics(Model model) {
        HealthDocumentService.DocumentStatistics stats = healthDocumentService.getDocumentStatistics();
        model.addAttribute("statistics", stats);
        return "health-documents/document-statistics";
    }

    // API endpoint để lấy tài liệu theo tag
    @GetMapping("/tag/{tag}")
    public String documentsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthDocumentDTO> documents = healthDocumentService.getDocumentsByTag(tag, page, size);
        model.addAttribute("documents", documents);
        model.addAttribute("tag", tag);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalItems", documents.getTotalElements());
        
        return "health-documents/documents-by-tag";
    }

    // API endpoint để lấy tài liệu theo tác giả
    @GetMapping("/author/{author}")
    public String documentsByAuthor(
            @PathVariable String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Page<HealthDocumentDTO> documents = healthDocumentService.getDocumentsByAuthor(author, page, size);
        model.addAttribute("documents", documents);
        model.addAttribute("author", author);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalItems", documents.getTotalElements());
        
        return "health-documents/documents-by-author";
    }
    
    // Test endpoint để tạo tài liệu mẫu
    @GetMapping("/test-create")
    @ResponseBody
    public String testCreateDocument() {
        try {
            HealthDocumentDTO testDoc = new HealthDocumentDTO();
            testDoc.setTitle("Tài liệu test");
            testDoc.setContent("Nội dung test");
            testDoc.setSummary("Tóm tắt test");
            testDoc.setAuthor("Test Author");
            testDoc.setCategory("GENERAL");
            testDoc.setType("ARTICLE");
            testDoc.setIsPublished(true);
            testDoc.setTags(new ArrayList<>());
            
            HealthDocumentDTO saved = healthDocumentService.createDocument(testDoc);
            return "Test document created successfully with ID: " + saved.getId();
        } catch (Exception e) {
            return "Error creating test document: " + e.getMessage();
        }
    }
} 