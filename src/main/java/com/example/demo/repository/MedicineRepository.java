package com.example.demo.repository;

import com.example.demo.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllByOrderByIdDesc();
}
