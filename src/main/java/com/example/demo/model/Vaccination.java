package com.example.demo.model;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate vaccinationDate;

    private String vaccineName;

    private String vaccineBatch;

    @Enumerated(EnumType.STRING)
    private VaccinationResult result = VaccinationResult.NORMAL;

    private String note;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private VaccinationCampaign vaccinationCampaign;

    // Getters and Setters
    
     public Long getId() {
        return id;
    }

    public LocalDate getVaccinationDate() {
        return vaccinationDate;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getVaccineBatch() {
        return vaccineBatch;
    }

    public VaccinationResult getResult() {
        return result;
    }

    public String getNote() {
        return note;
    }

    public Student getStudent() {
        return student;
    }

    public VaccinationCampaign getVaccinationCampaign() {
        return vaccinationCampaign;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void setVaccineBatch(String vaccineBatch) {
        this.vaccineBatch = vaccineBatch;
    }

    public void setResult(VaccinationResult result) {
        this.result = result;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setVaccinationCampaign(VaccinationCampaign vaccinationCampaign) {
        this.vaccinationCampaign = vaccinationCampaign;
    }
    
    public enum VaccinationResult {
        NORMAL,
        MILD_REACTION,
        UNDER_OBSERVATION
    }
}