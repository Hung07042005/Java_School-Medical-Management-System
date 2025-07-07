package com.example.demo.service;

import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository repository;

    public void save(Medicine supply) {
        repository.save(supply);
    }

    public List<Medicine> getAllSupplies() {
        return repository.findAllByOrderByIdDesc();
    }
}
