package com.example.demo.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class VaccinationConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private VaccinationStudent vaccinationStudent;

    private boolean parentAgreed;
    private String note;
    private LocalDate responseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VaccinationStudent getVaccinationStudent() {
        return vaccinationStudent;
    }

    public void setVaccinationStudent(VaccinationStudent vaccinationStudent) {
        this.vaccinationStudent = vaccinationStudent;
    }

    public boolean isParentAgreed() {
        return parentAgreed;
    }

    public void setParentAgreed(boolean parentAgreed) {
        this.parentAgreed = parentAgreed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }
}
