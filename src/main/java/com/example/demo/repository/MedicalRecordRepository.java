// src/main/java/com/example/demo/repository/MedicalRecordRepository.java
package com.example.demo.repository;

import com.example.demo.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByStudentId(Long studentId);
}