package com.example.demo.repository;

import com.example.demo.model.MedicalSupply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalSupplyRepository extends JpaRepository<MedicalSupply, Long> {
}
