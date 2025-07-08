package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Parent;
import com.example.demo.model.Student;
// import com.example.demo.repository.IParentRepository;
import com.example.demo.service.ParentService;
import com.example.demo.service.StudentService;

@Controller
@RequestMapping("/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private StudentService studentService;


    
    // hien thi them phu huynh
    @GetMapping("/new/{studentId}")
    public String showAddParentForm(@PathVariable Long studentId, Model model) {
        model.addAttribute("parent", new Parent());
        model.addAttribute("studentId", studentId);
        return "parent/parent-form";
    }
    // chinh sua phu huynh 
    @GetMapping("/edit/{parentId}")
    public String showEditParentForm(@PathVariable Long parentId, Model model) {
        Parent parent = parentService.getParentByIDWithChildren(parentId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));

        model.addAttribute("parent", parent);
        return "parent/parent-edit-form"; // form riêng cho chỉnh sửa
    }
    @PostMapping("/update")
    public String updateParent(@ModelAttribute Parent parent, RedirectAttributes redirectAttributes) {
        // Lấy parent cũ từ DB để giữ lại thông tin student
        Parent existingParent = parentService.getParentByIDWithChildren(parent.getId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));

        // Gán lại student cũ vào parent mới
        parent.setChildren(existingParent.getChildren());
        parentService.saveParent(parent);

        redirectAttributes.addFlashAttribute("message", "Cập nhật phụ huynh thành công!");
        return "redirect:/students";
    }


   // them phu huynh
    @PostMapping("/save")
    public String saveParent(@ModelAttribute Parent parent,
                            @RequestParam Long studentId,
                            RedirectAttributes redirectAttributes) {
        Student student = studentService.getStudentById(studentId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh"));

        // Lưu phụ huynh
        Parent savedParent = parentService.saveParent(parent);

        // Gắn phụ huynh vào học sinh
        student.setParent(savedParent);
        studentService.saveStudent(student); // ⬅ Đảm bảo có dòng này!

        redirectAttributes.addFlashAttribute("message", "Thêm phụ huynh thành công!");
        return "redirect:/students";
    }

    // hien xem chi tiet phu huynh
    @GetMapping("/detail/{parent_id}/students")
    public String viewParentStudents(@PathVariable Long parent_id, Model model) {
        Parent parent = parentService.getParentByIDWithChildren(parent_id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));

        List<Student> children = studentService.getStudentByParentID(parent_id);

        model.addAttribute("parent", parent);
        model.addAttribute("students", children);
        return "parent/parent-detail"; 
    }
    
    // xoa phu huynh
    @PostMapping("/delete/{id}")
    public String deleteParent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        parentService.deleteParentByID(id);
        redirectAttributes.addFlashAttribute("message", "Xóa phụ huynh thành công!");
        return "redirect:/students";
    }


    
}

