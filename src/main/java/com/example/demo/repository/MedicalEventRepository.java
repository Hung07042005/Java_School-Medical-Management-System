package com.example.demo.repository;

import com.example.demo.model.MedicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalEventRepository extends JpaRepository<MedicalEvent, Long> {
}
