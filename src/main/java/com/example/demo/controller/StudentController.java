package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.dto.StudentDTO; // Import StudentDTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*; // Import tất cả các annotation cần thiết
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Để gửi thông báo sau redirect

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // --- Chức năng Hiển thị Danh sách ---
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student-list"; // Trả về template student-list.html
    }

    // --- Chức năng Thêm mới Học sinh (Hiển thị form) ---
    @GetMapping("/new")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new StudentDTO()); // Gửi một StudentDTO rỗng để tạo form
        return "student-form"; // Trả về template student-form.html
    }

    // --- Chức năng Thêm mới/Cập nhật Học sinh (Xử lý submit form) ---
    @PostMapping
    public String saveStudent(@ModelAttribute("student") StudentDTO studentDTO, RedirectAttributes redirectAttributes) {
        // @ModelAttribute sẽ tự động ánh xạ dữ liệu từ form vào StudentDTO
        try {
            studentService.saveStudentFromDTO(studentDTO);
            redirectAttributes.addFlashAttribute("message", "Student saved successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error saving student: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/students"; // Chuyển hướng về trang danh sách sau khi lưu
    }

    // --- Chức năng Sửa Học sinh (Hiển thị form với dữ liệu cũ) ---
    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.getStudentById(id).map(student -> {
            // Chuyển đổi Entity Student sang StudentDTO để hiển thị trên form
            StudentDTO studentDTO = new StudentDTO(
                student.getId(),
                student.getUsername(),
                student.getFullName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getStudentId(),
                student.getDateOfBirth(),
                student.getStudentClass()
            );
            model.addAttribute("student", studentDTO);
            return "student-form"; // Dùng lại template student-form.html
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/students";
        });
    }

    // --- Chức năng Xóa Học sinh ---
    @PostMapping("/delete/{id}") // Dùng POST cho hành động xóa để tránh CSRF (trong thực tế)
    // Hoặc @GetMapping("/delete/{id}") nếu bạn muốn dùng link đơn giản
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting student: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/students"; // Chuyển hướng về trang danh sách
    }

    // Bạn có thể giữ lại HelloController cũ nếu muốn, hoặc bỏ đi
    // @GetMapping("/")
    // public String hello() {
    //     return "Hello from Spring Boot! Your application is running.";
    // }
}