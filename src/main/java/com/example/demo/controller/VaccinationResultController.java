package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Parent;
import com.example.demo.model.Student;
import com.example.demo.model.VaccinationStudent;
import com.example.demo.service.ParentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.VaccinationStudentService;

@Controller
public class VaccinationResultController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private VaccinationStudentService vaccinationStudentService;

    @GetMapping("/parent/vaccination-results/{id}")
    public String showVaccinationResults(@PathVariable Long id, Model model) {
        Parent parent = parentService.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));
        List<Student> children = studentService.findByParent(parent);
        List<VaccinationStudent> vaccinations = vaccinationStudentService.findByStudents(children);

        model.addAttribute("vaccinations", vaccinations);
        return "parent/vaccination-result";
    }
    
}