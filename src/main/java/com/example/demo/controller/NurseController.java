package com.example.demo.controller;

import com.example.demo.model.MedicalEvent;
import com.example.demo.model.Medicine;
import com.example.demo.service.MedicalEventService;
import com.example.demo.repository.MedicineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicalEventService medicalEventService;

    // --- Quản lý thuốc & vật tư ---
    @GetMapping("/medical-supplies")
    public String showMedicines(Model model) {
        model.addAttribute("medicines", medicineRepository.findAllByOrderByIdDesc());
        return "nurse/medical-supplies";
    }
    // ✅ Xác nhận đã cho uống
    @PostMapping("/confirm-given/{id}")
    public String confirmGiven(@PathVariable Long id) {
        medicineRepository.findById(id).ifPresent(med -> {
            med.setGiven(true);    // ✅ Đúng

            medicineRepository.save(med);
        });
        return "redirect:/nurse/medical-supplies";
    }

    // --- Ghi nhận sự kiện y tế ---
    @GetMapping("/record-event")
    public String showMedicalEventForm(Model model) {
        model.addAttribute("event", new MedicalEvent());
        return "nurse/medical-event-form";
    }

    @PostMapping("/record-event")
    public String saveMedicalEvent(@ModelAttribute MedicalEvent event,
                                   @RequestParam("eventTime") String timeInput) {
        event.setEventTime(LocalDateTime.parse(timeInput));
        medicalEventService.saveEvent(event);
        return "redirect:/nurse/event-list";
    }

    @GetMapping("/event-list")
    public String showMedicalEvents(Model model) {
        model.addAttribute("events", medicalEventService.getAllEvents());
        return "nurse/medical-event-list";
    }
}
