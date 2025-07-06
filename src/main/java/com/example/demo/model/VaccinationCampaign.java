package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class VaccinationCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campaignName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String note;

    @OneToMany(mappedBy = "vaccinationCampaign", cascade = CascadeType.ALL)
    private List<Vaccination> vaccinations;

     // Constructors
    public VaccinationCampaign() {

    }

    public VaccinationCampaign(String campaignName, LocalDate startDate, LocalDate endDate, String note) {
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }
     public void addVaccination(Vaccination vaccination) {
        vaccinations.add(vaccination);
        vaccination.setVaccinationCampaign(this);
    }

    public void removeVaccination(Vaccination vaccination) {
        vaccinations.remove(vaccination);
        vaccination.setVaccinationCampaign(null);
    }
}
