package com.example.demo.controller;

import com.example.demo.model.MedicalEvent;
import com.example.demo.service.MedicalEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/nurse")
public class MedicalEventController {

    @Autowired
    private MedicalEventService service;

    @GetMapping("/record-event")
    public String showForm(Model model) {
        model.addAttribute("event", new MedicalEvent());
        return "nurse/medical-event-form"; // đúng tên file và folder
    }

    @PostMapping("/record-event")
    public String saveEvent(@ModelAttribute MedicalEvent event,
                            @RequestParam("eventTime") String timeInput) {
        event.setEventTime(LocalDateTime.parse(timeInput));
        service.saveEvent(event);
        return "redirect:/nurse/event-list";
    }

    @GetMapping("/event-list")
    public String showEvents(Model model) {
        model.addAttribute("events", service.getAllEvents());
        return "nurse/medical-event-list"; // đúng tên file
    }
}
