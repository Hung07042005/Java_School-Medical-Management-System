// src/main/java/com/example/demo/controller/MedicalRecordController.java
package com.example.demo.controller;

import com.example.demo.dto.MedicalRecordDTO;
import com.example.demo.model.MedicalRecord;
import com.example.demo.model.Student;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private StudentService studentService; // Để kiểm tra sự tồn tại của học sinh

    // Hiển thị danh sách học sinh để phụ huynh chọn (cho role PARENT)
    @GetMapping("/select-student")
    public String showStudentSelectionForm(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "medical-record-select-student"; // Template mới để chọn học sinh
    }

    // Hiển thị danh sách học sinh để xem hồ sơ y tế (cho role ADMIN, MANAGER, NURSE)
    @GetMapping("/view-select-student")
    public String showStudentSelectionForView(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "medical-record-view-select-student"; // Template để chọn học sinh xem hồ sơ
    }

    // Hiển thị hồ sơ y tế của học sinh (chỉ xem, không chỉnh sửa)
    @GetMapping("/view/{studentId}")
    public String viewMedicalRecord(@PathVariable Long studentId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOptional = studentService.getStudentById(studentId);
        if (studentOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/students";
        }

        Student student = studentOptional.get();
        model.addAttribute("student", student);

        Optional<MedicalRecord> existingRecord = medicalRecordService.getMedicalRecordByStudentId(studentId);
        if (existingRecord.isPresent()) {
            MedicalRecord record = existingRecord.get();
            MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(
                record.getId(),
                record.getStudent().getId(),
                record.getAllergies(),
                record.getChronicConditions(),
                record.getPastMedicalHistory(),
                record.getVisionLeft(),
                record.getVisionRight(),
                record.getHearingStatus()
            );
            model.addAttribute("medicalRecord", medicalRecordDTO);
            model.addAttribute("hasRecord", true);
        } else {
            model.addAttribute("hasRecord", false);
            model.addAttribute("medicalRecord", new MedicalRecordDTO());
        }
        
        return "medical-record-view"; // Template chỉ để xem hồ sơ
    }

    // Hiển thị form để khai báo/cập nhật hồ sơ sức khỏe cho một học sinh cụ thể
    @GetMapping("/student/{studentId}")
    public String showMedicalRecordForm(@PathVariable Long studentId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> studentOptional = studentService.getStudentById(studentId);
        if (studentOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Student not found!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/students";
        }

        Student student = studentOptional.get();
        model.addAttribute("student", student); // Truyền thông tin học sinh vào model

        Optional<MedicalRecord> existingRecord = medicalRecordService.getMedicalRecordByStudentId(studentId);
        MedicalRecordDTO medicalRecordDTO;

        if (existingRecord.isPresent()) {
            // Nếu đã có hồ sơ, điền dữ liệu vào DTO
            MedicalRecord record = existingRecord.get();
            medicalRecordDTO = new MedicalRecordDTO(
                record.getId(),
                record.getStudent().getId(),
                record.getAllergies(),
                record.getChronicConditions(),
                record.getPastMedicalHistory(),
                record.getVisionLeft(),
                record.getVisionRight(),
                record.getHearingStatus()
            );
        } else {
            // Nếu chưa có, tạo DTO rỗng với studentId
            medicalRecordDTO = new MedicalRecordDTO();
            medicalRecordDTO.setStudentId(studentId);
            medicalRecordDTO.setAllergies(new ArrayList<>()); // Khởi tạo list rỗng
            medicalRecordDTO.setChronicConditions(new ArrayList<>()); // Khởi tạo list rỗng
        }
        model.addAttribute("medicalRecord", medicalRecordDTO);
        return "medical-record-form"; // Trả về template medical-record-form.html
    }

    // Xử lý submit form khai báo/cập nhật hồ sơ sức khỏe
    @PostMapping
    public String saveMedicalRecord(@ModelAttribute("medicalRecord") MedicalRecordDTO medicalRecordDTO,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam(required = false) String returnTo) {
        try {
            medicalRecordService.saveMedicalRecordFromDTO(medicalRecordDTO);
            redirectAttributes.addFlashAttribute("message", "Medical record saved successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Error saving medical record: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        // Nếu returnTo = "select", chuyển về trang chọn học sinh (cho phụ huynh)
        if ("select".equals(returnTo)) {
            return "redirect:/medical-records/select-student";
        }
        
        return "redirect:/students"; // Mặc định chuyển về trang danh sách học sinh
    }
}