package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Parent;
import com.example.demo.model.Student;
import com.example.demo.model.VaccinationStudent;

@Repository
public interface IVaccinationStudentRepository extends JpaRepository<VaccinationStudent, Long> {
    // Dành cho phụ huynh: tìm danh sách phiếu tiêm chủng liên quan đến học sinh của họ
    List<VaccinationStudent> findByStudent_Parent(Parent parent);

    // Dùng khi cần lấy theo nhiều học sinh (trong controller VaccinationResultController)
    List<VaccinationStudent> findByStudentIn(List<Student> students);
}

