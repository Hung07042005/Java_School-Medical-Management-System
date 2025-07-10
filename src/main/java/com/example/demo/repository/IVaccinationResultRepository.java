package com.example.demo.repository;

import com.example.demo.model.VaccinationResult;
import com.example.demo.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVaccinationResultRepository extends JpaRepository<VaccinationResult, Long> {

    // Lấy danh sách kết quả tiêm của học sinh có phụ huynh là parent
    List<VaccinationResult> findByVaccinationStudent_Student_Parent(Parent parent);
}

