package com.example.demo.service;

import com.example.demo.model.Vaccine;
import com.example.demo.repository.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private IVaccineRepository vaccineRepository;

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public void saveVaccine(Vaccine vaccine) {
        vaccineRepository.save(vaccine);
    }

    public void deleteVaccine(Long id) {
        vaccineRepository.deleteById(id);
    }

    public Vaccine getVaccineById(Long id) {
        return vaccineRepository.findById(id).orElse(null);
    }

    public Vaccine findById(Long id) {
        return vaccineRepository.findById(id) 
            .orElseThrow(() -> new RuntimeException("Không tìm thấy vaccin có ID này!"));
    }

    public List<Vaccine> findAll() {
        return vaccineRepository.findAll();
    }
}

