
package com.example.demo.controller;


import com.example.demo.model.VaccinationCampaign;
import com.example.demo.service.VaccinationCampaignService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/campaigns")
public class VaccinationCampaignController {

    @Autowired
    private VaccinationCampaignService vaccinationCampaignService;

    // Danh sách campaign
    @GetMapping("")
    public String listCampaigns(Model model) {
        model.addAttribute("campaigns", vaccinationCampaignService.getAllCampaigns());
        return "/manager/vaccination-campaign-list";
    }

    //Form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("campaign", new VaccinationCampaign());
        return "/manager/vaccination-campaign-form";
    }

    // Xử lý thêm mới
    @PostMapping("/save")
    public String saveCampaign(@ModelAttribute("campaign") VaccinationCampaign campaign) {
        vaccinationCampaignService.saveCampaign(campaign);
        return "redirect:/campaigns";
    }

    // Form cập nhật
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VaccinationCampaign campaign = vaccinationCampaignService.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("campaign", campaign);
        return "/manager/vaccination-campaign-form";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteCampaign(@PathVariable Long id) {
        vaccinationCampaignService.deleteCampaignById(id);
        return "redirect:/campaigns";
    }
    // lọc
    @GetMapping("/filter")
    public String filterCampaigns(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        Model model
    ) {
        List<VaccinationCampaign> campaigns;

        if (startDate != null && endDate != null) {
            campaigns = vaccinationCampaignService.filterByStartDate(startDate, endDate);
        } else {
            campaigns = vaccinationCampaignService.getAllCampaigns();
        }

        model.addAttribute("campaigns", campaigns);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "vaccination-campaign-list";
 
    }
    // chi tiet campaign
    
   
}


