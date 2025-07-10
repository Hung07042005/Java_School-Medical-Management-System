// src/main/java/com/example/demo/dto/StudentDTO.java
package com.example.demo.dto;

import java.time.LocalDate;

// Đây là một DTO đơn giản dùng cho việc thêm/sửa học sinh.
// Nó không bao gồm các trường nhạy cảm như password nếu không cần thiết.
public class StudentDTO {
    private Long id; // Dùng cho trường hợp sửa (edit), có thể null khi thêm mới
    private String fullName;
    private String email;
    private String phoneNumber;
    private String studentId;
    private LocalDate dateOfBirth;
    private String studentClass;

    // Constructors
    public StudentDTO() {}

    public StudentDTO(Long id, String fullName, String email, String phoneNumber,
                      String studentId, LocalDate dateOfBirth, String studentClass) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.studentClass = studentClass;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}