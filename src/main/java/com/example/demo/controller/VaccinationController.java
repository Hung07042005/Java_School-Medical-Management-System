package com.example.demo.controller;

import com.example.demo.model.Vaccination;
import com.example.demo.model.VaccinationCampaign;
import com.example.demo.service.StudentService;
import com.example.demo.service.VaccinationCampaignService;
import com.example.demo.service.VaccinationService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private VaccinationCampaignService campaignService;

    @GetMapping("/campaign/{campaignId}")
    public String viewVaccinationsInCampaign(@PathVariable Long campaignId, Model model) {
        Optional<VaccinationCampaign> campaignOptional = campaignService.getById(campaignId);
        if (campaignOptional.isEmpty()) {
            return "redirect:/vaccinations?error=notfound";
        }

        VaccinationCampaign campaign = campaignOptional.get();
        model.addAttribute("campaign", campaign);

        List<Vaccination> vaccinations = vaccinationService.findByVaccinationCampaignId(campaignId);
        model.addAttribute("vaccinations", vaccinations);

        // Các lớp đã tham gia
        Set<String> joinedClasses = vaccinations.stream()
            .map(v -> v.getStudent().getStudentClass())
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        // Tất cả các lớp tồn tại trong hệ thống
        List<String> allClasses = studentService.findAllClassNames();

        // Các lớp chưa tham gia = tất cả - lớp đã tham gia
        List<String> notJoinedClasses = allClasses.stream()
            .filter(cls -> !joinedClasses.contains(cls))
            .collect(Collectors.toList());

        model.addAttribute("classList", joinedClasses);
        model.addAttribute("notJoinedClassList", notJoinedClasses);

        return "manager/campaign-detail";
    }




    // hien thi ds hoc sinh theo lop
    @GetMapping("/campaign/{campaignId}/class/{className}")
    public String viewStudentsInClassAndCampaign(@PathVariable Long campaignId,
                                                @PathVariable String className,
                                                Model model) {
        List<Vaccination> vaccinations = vaccinationService.findByVaccinationCampaignId(campaignId).stream()
            .filter(v -> className.equalsIgnoreCase(v.getStudent().getStudentClass()))
            .toList();

        VaccinationCampaign campaign = vaccinations.isEmpty() ? null : vaccinations.get(0).getVaccinationCampaign();

        model.addAttribute("vaccinations", vaccinations);
        model.addAttribute("className", className);
        model.addAttribute("campaign", campaign);

        return "manager/student-in-class";
    }


    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("vaccination", new Vaccination());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "vaccination/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("vaccination") Vaccination vaccination) {
        vaccinationService.saveVaccination(vaccination);
        return "redirect:/vaccinations";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Vaccination vaccination = vaccinationService.getVaccinationById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("vaccination", vaccination);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "vaccination/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        vaccinationService.deleteVaccinationById(id);
        return "redirect:/vaccinations";
    }

    @GetMapping("/by-campaign/{campaignId}")
    public String getClassesByCampaign(@PathVariable Long campaignId, Model model) {
        List<Vaccination> vaccinations = vaccinationService.findByVaccinationCampaignId(campaignId);

        // Lấy danh sách lớp từ các học sinh
        Set<String> classList = vaccinations.stream() // dung set để tránh lặp lại tên học sinh
                .map(v -> v.getStudent().getStudentClass()) // hoặc getClassName()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new)); // sắp xếp tăng dần và loại trùng

        VaccinationCampaign campaign = vaccinations.isEmpty() ? null : vaccinations.get(0).getVaccinationCampaign();

        model.addAttribute("classList", classList);
        model.addAttribute("campaign", campaign);
        return "vaccination/class_list"; // Tên file HTML
    }

    
}

