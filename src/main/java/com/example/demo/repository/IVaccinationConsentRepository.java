package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.VaccinationConsent;

@Repository
public interface IVaccinationConsentRepository extends JpaRepository<VaccinationConsent, Long> {
    VaccinationConsent findByVaccinationStudent_Id(Long vaccinationStudentId); // truy cập theo id của bảng con
}
