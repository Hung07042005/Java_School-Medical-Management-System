package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Vaccination;
import com.example.demo.repository.IVaccinationRepository;


@Service
public class VaccinationService {

    @Autowired
    private IVaccinationRepository vaccinationRepository;

    public List<Vaccination> getAllVaccination() {
        return vaccinationRepository.findAll();
    }

    public Optional<Vaccination> getVaccinationById(Long id) {
        return vaccinationRepository.findById(id);
    }

    public void saveVaccination(Vaccination vaccination) {
        vaccinationRepository.save(vaccination);
    }

    public void deleteVaccinationById(Long id) {
        vaccinationRepository.deleteById(id);
    }

    // public List<Student> getStudentsByCampaign(Long campaignId) {
    //     List<Vaccination> vaccinations = vaccinationRepository.findByVaccinationCampaignId(campaignId);
    //     return vaccinations.stream()
    //             .map(Vaccination::getStudent)
    //             .distinct() // Tránh trùng học sinh nếu một học sinh tiêm nhiều lần
    //             .collect(Collectors.toList());
    // }
    public List<Vaccination> findByVaccinationCampaignId(Long campaignId) {
        return vaccinationRepository.findByVaccinationCampaign_Id(campaignId);
    }
    
}