package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student extends User { // Kế thừa từ User

    private String studentId; // Mã số học sinh
    private LocalDate dateOfBirth;
    private String studentClass; // Lớp học (ví dụ: "10A1")

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalRecord medicalRecord; // Mỗi học sinh có một hồ sơ sức khỏe

    // Constructors
    public Student() {
    }

    public Student(String username, String password, String fullName, String email, String phoneNumber,
                   String studentId, LocalDate dateOfBirth, String studentClass) {
        super(username, password, fullName, email, phoneNumber);
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.studentClass = studentClass;
    }

    // Getters and Setters
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

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
        if (medicalRecord != null) {
            medicalRecord.setStudent(this);
        }
    }
}