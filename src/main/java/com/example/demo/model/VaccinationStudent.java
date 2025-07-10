package com.example.demo.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;



@Entity
public class VaccinationStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Vaccine vaccine;

    @Enumerated(EnumType.STRING)
    private VaccinationStatus status = VaccinationStatus.PENDING;

    @OneToOne(mappedBy = "vaccinationStudent", cascade = CascadeType.ALL)
    private VaccinationConsent consent;

    @OneToOne(mappedBy = "vaccinationStudent", cascade = CascadeType.ALL)
    private VaccinationResult result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccination(Vaccine vaccination) {
        this.vaccine = vaccination;
    }

    public VaccinationStatus getStatus() {
        return status;
    }

    public void setStatus(VaccinationStatus status) {
        this.status = status;
    }

    public VaccinationConsent getConsent() {
        return consent;
    }

    public void setConsent(VaccinationConsent consent) {
        this.consent = consent;
    }

    public VaccinationResult getResult() {
        return result;
    }

    public void setResult(VaccinationResult result) {
        this.result = result;
    }
}

