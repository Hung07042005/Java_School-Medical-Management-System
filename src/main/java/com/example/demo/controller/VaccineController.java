package com.example.demo.controller;

import com.example.demo.model.Vaccine;
import com.example.demo.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    // Danh sách các đợt tiêm
    @GetMapping("/vaccines")
    public String listVaccines(Model model) {
        List<Vaccine> vaccines = vaccineService.findAll();  // dùng phương thức mới
        model.addAttribute("vaccines", vaccines);
        return "vaccine/list";
    }

    // Hiển thị form tạo đợt tiêm mới
    @GetMapping("/vaccines/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vaccine", new Vaccine());
        return "vaccine/form"; // Form thêm mới
    }

    // Lưu đợt tiêm (tạo mới hoặc cập nhật)
    @PostMapping("/vaccines/save")
    public String saveVaccine(@ModelAttribute("vaccine") Vaccine vaccine) {
        vaccineService.saveVaccine(vaccine);
        return "redirect:/vaccines";
    }

    // Xoá đợt tiêm
    @GetMapping("/vaccines/delete/{id}")
    public String deleteVaccine(@PathVariable("id") Long id) {
        vaccineService.deleteVaccine(id);
        return "redirect:/vaccines";
    }

    // Hiển thị form sửa
    @GetMapping("/vaccines/edit/{id}")
    public String editVaccine(@PathVariable("id") Long id, Model model) {
        Vaccine vaccine = vaccineService.getVaccineById(id);
        model.addAttribute("vaccine", vaccine);
        return "vaccine/form";
    }
    @GetMapping("/vaccines/{id}/students")
    public String viewDetails(@PathVariable Long id, Model model) {
        Vaccine vaccine = vaccineService.findById(id);
        model.addAttribute("vaccine", vaccine);
        return "vaccine/detail";
    }

}
