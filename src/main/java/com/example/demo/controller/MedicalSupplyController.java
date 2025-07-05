package com.example.demo.controller;

import com.example.demo.model.MedicalSupply;
import com.example.demo.service.MedicalSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nurse")
public class MedicalSupplyController {

    @Autowired
    private MedicalSupplyService service;

    @GetMapping("/medical-supplies")
    public String viewSupplies(Model model) {
        model.addAttribute("supplies", service.findAll());
        return "nurse/medical-supplies"; // Đặt file .html của bạn vào thư mục này
    }

    @PostMapping("/medical-supplies")
    public String saveSupply(@ModelAttribute MedicalSupply supply) {
        service.save(supply);
        return "redirect:/nurse/medical-supplies";
    }
}
