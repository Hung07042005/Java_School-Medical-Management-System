package com.example.demo.controller;

import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/parent")
public class ParentMedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/send-medicine")
    public String showSendForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "parent/send-medicine";
    }

    @PostMapping("/send-medicine")
    public String submitMedicine(@ModelAttribute Medicine medicine) {
        medicineRepository.save(medicine);
        return "redirect:/parent/send-medicine?success";
    }
}
