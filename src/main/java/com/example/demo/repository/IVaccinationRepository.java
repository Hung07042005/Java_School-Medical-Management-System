package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Vaccination;

public interface IVaccinationRepository extends JpaRepository<Vaccination, Long> {
    // public List<Vaccination> findByVaccinationCampaignId(Long campaignId);

    List<Vaccination> findByVaccinationCampaign_Id(Long campaignId);
}