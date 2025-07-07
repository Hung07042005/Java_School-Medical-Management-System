package com.example.demo.service;

import com.example.demo.model.MedicalEvent;
import com.example.demo.repository.MedicalEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalEventService {

    @Autowired
    private MedicalEventRepository repository;

    public void saveEvent(MedicalEvent event) {
        repository.save(event);
    }

    public List<MedicalEvent> getAllEvents() {
        return repository.findAll();
    }
}
