// src/main/java/com/example/demo/repository/StudentRepository.java
package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần, ví dụ:
    // Optional<Student> findByStudentId(String studentId);
    // List<Student> findByStudentClass(String studentClass);
}