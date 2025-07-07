package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Hàm chuẩn hóa tên tiếng Việt
    private String chuanHoaTen(String ten) {
        if (ten == null || ten.isBlank()) return ten;
        String[] words = ten.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)));
                if (w.length() > 1) sb.append(w.substring(1));
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Phương thức để lưu (thêm mới hoặc cập nhật) học sinh từ DTO
    public Student saveStudentFromDTO(StudentDTO studentDTO) {
        Student student;
        String fullNameChuanHoa = chuanHoaTen(studentDTO.getFullName());
        if (studentDTO.getId() != null) { // Nếu có ID, tức là đang cập nhật
            student = studentRepository.findById(studentDTO.getId())
                                     .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentDTO.getId()));
            // Cập nhật các trường từ DTO vào Entity hiện có
            student.setUsername(studentDTO.getUsername());
            student.setFullName(fullNameChuanHoa);
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
            student.setFullName(fullNameChuanHoa);
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

    public List<Student> getStudentsByCampaign(Long campaignId) {
        return studentRepository.findStudentsByCampaignId(campaignId);
    }

    public List<Student> getStudentsByCampaignAndClass(Long campaignId, String studentClass) {
        return studentRepository.findStudentsByCampaignIdAndClass(campaignId, studentClass);
    }

    public List<String> getClassListFromCampaign(Long campaignId) {
        return studentRepository.findStudentsByCampaignId(campaignId).stream()
                .map(Student::getStudentClass)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<String> findAllClassNames() {
        return studentRepository.findAll().stream()
            .map(Student::getStudentClass)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

}