package com.example.demo.service;

import com.example.demo.model.Parent;
import com.example.demo.model.Student;
import com.example.demo.model.VaccinationStudent;
import com.example.demo.repository.IVaccinationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationStudentService {

    @Autowired
    private IVaccinationStudentRepository vaccinationStudentRepository;

    // Dùng trong xác nhận tiêm
    public List<VaccinationStudent> findByParent(Parent parent) {
        return vaccinationStudentRepository.findByStudent_Parent(parent);
    }

    // Dùng trong kết quả tiêm (có danh sách học sinh)
    public List<VaccinationStudent> findByStudents(List<Student> students) {
        return vaccinationStudentRepository.findByStudentIn(students);
    }

    // Dùng trong consent form
    public VaccinationStudent findById(Long id) {
        return vaccinationStudentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi VaccinationStudent với ID = " + id));
    }

    public VaccinationStudent save(VaccinationStudent vs) {
        return vaccinationStudentRepository.save(vs);
    }

}
