package com.example.demo.controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Parent;
import com.example.demo.model.VaccinationConsent;
import com.example.demo.model.VaccinationStatus;
import com.example.demo.model.VaccinationStudent;
import com.example.demo.service.ParentService;
import com.example.demo.service.VaccinationConsentService;
import com.example.demo.service.VaccinationStudentService;

@Controller
@RequestMapping("/vaccination")
public class VaccinationConsentController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private VaccinationConsentService vaccinationConsentService;

    @Autowired
    private VaccinationStudentService vaccinationStudentService;

    @GetMapping("/vaccination/parent/consent/{id}")
    public String listConsentForms(@PathVariable Long id, Model model) {
        Parent parent = parentService.findById(id) 
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));
        List<VaccinationStudent> vaccinationList = vaccinationStudentService.findByParent(parent);
        model.addAttribute("vaccinationList", vaccinationList);
        return "vaccine/parent-consent";
    }   


    @GetMapping("/consent/form/{id}")
    public String showConsentForm(@PathVariable Long id, Model model) {
        VaccinationStudent vs = vaccinationStudentService.findById(id);
        VaccinationConsent consent = vs.getConsent();
        if (consent == null) {
            consent = new VaccinationConsent();
            consent.setVaccinationStudent(vs);
        }
        model.addAttribute("consent", consent);
        return "vaccine/consent-form";
    }

    @PostMapping("/consent/save")
    public String saveConsent(@ModelAttribute VaccinationConsent consent) {
        consent.setResponseDate(LocalDate.now());

        VaccinationStudent vs = vaccinationStudentService.findById(consent.getVaccinationStudent().getId());
        if (consent.isParentAgreed()) {
            vs.setStatus(VaccinationStatus.CONFIRMED);
        } else {
            vs.setStatus(VaccinationStatus.REFUSE);
        }

        vaccinationStudentService.save(vs);
        vaccinationConsentService.save(consent);
        return "redirect:/vaccination/parent/consent";
    }

}

