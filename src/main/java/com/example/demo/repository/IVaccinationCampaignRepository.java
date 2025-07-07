package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.VaccinationCampaign;

public interface IVaccinationCampaignRepository extends JpaRepository<VaccinationCampaign, Long> {

    List<VaccinationCampaign> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}