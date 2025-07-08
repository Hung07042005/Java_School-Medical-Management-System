// src/main/java/com/example/demo/repository/StudentRepository.java
package com.example.demo.repository;

import com.example.demo.model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
     // Tìm học sinh theo lớp
    List<Student> findByStudentClass(String studentClass);


    // Tìm học sinh theo campaign (thông qua bảng Vaccination)
    @Query("SELECT DISTINCT v.student FROM Vaccination v WHERE v.vaccinationCampaign.id = :campaignId")
    List<Student> findStudentsByCampaignId(Long campaignId);

    // Tìm học sinh theo campaign + class
    @Query("SELECT DISTINCT v.student FROM Vaccination v WHERE v.vaccinationCampaign.id = :campaignId AND v.student.studentClass = :studentClass")
    List<Student> findStudentsByCampaignIdAndClass(Long campaignId, String studentClass);

    // tim danh sach ten lop
    @Query("SELECT DISTINCT s.studentClass FROM Student s WHERE s.studentClass IS NOT NULL")
    List<String> findAllClassNames();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.parent")
    List<Student> findAllWithParent();

    @Query("SELECT s FROM Student s WHERE s.parent.id = :parentId")
    List<Student> findStudentsByParentId(@Param("parentId") Long parentId);
}