package com.example.demo.service;

import com.example.demo.model.MedicalSupply;
import com.example.demo.repository.MedicalSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalSupplyService {

    @Autowired
    private MedicalSupplyRepository repository;

    public void save(MedicalSupply supply) {
        repository.save(supply);
    }

    public List<MedicalSupply> findAll() {
        return repository.findAll();
    }
}
