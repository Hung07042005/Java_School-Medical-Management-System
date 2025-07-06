package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.VaccinationCampaign;
import com.example.demo.repository.IVaccinationCampaignRepository;
@Service
public class VaccinationCampaignService {

    @Autowired
    private IVaccinationCampaignRepository vaccinationCampaignRepository;

    public List<VaccinationCampaign> getAllCampaigns() {
        return vaccinationCampaignRepository.findAll();
    }
    
    public Optional<VaccinationCampaign> getById(Long id) {
        return vaccinationCampaignRepository.findById(id);
    }
    
    public List<VaccinationCampaign> filterByStartDate(LocalDate start, LocalDate end) {
        return vaccinationCampaignRepository.findByStartDateBetween(start, end);
    }

    public void saveCampaign(VaccinationCampaign campaign) {
        vaccinationCampaignRepository.save(campaign);
    }

    public void deleteCampaignById(Long id) {
        vaccinationCampaignRepository.deleteById(id);
    }
}
