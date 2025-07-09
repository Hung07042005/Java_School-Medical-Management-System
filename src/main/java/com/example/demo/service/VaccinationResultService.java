package com.example.demo.service;

import com.example.demo.model.Parent;
import com.example.demo.model.VaccinationResult;
import com.example.demo.repository.IVaccinationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationResultService {

    @Autowired
    private IVaccinationResultRepository vaccinationResultRepository;

    // Lấy kết quả tiêm cho phụ huynh (theo học sinh)
    public List<VaccinationResult> getResultsByParent(Parent parent) {
        return vaccinationResultRepository.findByVaccinationStudent_Student_Parent(parent);
    }
}
