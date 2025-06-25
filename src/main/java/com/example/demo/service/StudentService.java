package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.StudentDTO; // Import StudentDTO

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Phương thức để lưu (thêm mới hoặc cập nhật) học sinh từ DTO
    public Student saveStudentFromDTO(StudentDTO studentDTO) {
        Student student;
        if (studentDTO.getId() != null) { // Nếu có ID, tức là đang cập nhật
            student = studentRepository.findById(studentDTO.getId())
                                     .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentDTO.getId()));
            // Cập nhật các trường từ DTO vào Entity hiện có
            student.setUsername(studentDTO.getUsername());
            student.setFullName(studentDTO.getFullName());
            student.setEmail(studentDTO.getEmail());
            student.setPhoneNumber(studentDTO.getPhoneNumber());
            student.setStudentId(studentDTO.getStudentId());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            student.setStudentClass(studentDTO.getStudentClass());
            // Lưu ý: Không cập nhật password trực tiếp từ DTO này trong thực tế.
            // Password sẽ được xử lý riêng (ví dụ: đổi mật khẩu)
        } else { // Nếu không có ID, tức là thêm mới
            student = new Student();
            student.setUsername(studentDTO.getUsername());
            // Mật khẩu tạm thời hoặc sẽ được đặt sau (ví dụ: random, hoặc yêu cầu đặt khi đăng ký)
            // Cần có logic để quản lý mật khẩu an toàn
            student.setPassword("temp_password"); // Tạm thời để vượt qua lỗi nullable = false
            student.setFullName(studentDTO.getFullName());
            student.setEmail(studentDTO.getEmail());
            student.setPhoneNumber(studentDTO.getPhoneNumber());
            student.setStudentId(studentDTO.getStudentId());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            student.setStudentClass(studentDTO.getStudentClass());
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // TODO: Trong thực tế, cần thêm logic kiểm tra trùng lặp username, studentId
    // TODO: Xử lý mật khẩu một cách an toàn (ví dụ: dùng BcryptPasswordEncoder)
}