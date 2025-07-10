package com.example.demo.service;

import com.example.demo.model.VaccinationConsent;
import com.example.demo.repository.IVaccinationConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationConsentService {

    @Autowired
    private IVaccinationConsentRepository vaccinationConsentRepository;

    public void save(VaccinationConsent consent) {
        vaccinationConsentRepository.save(consent);
    }

    public VaccinationConsent findById(Long id) {
        return vaccinationConsentRepository.findById(id).orElse(null);
    }
}
