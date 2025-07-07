package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student; // Liên kết với một học sinh

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "allergies", joinColumns = @JoinColumn(name = "medical_record_id"))
    @Column(name = "allergy_name")
    private List<String> allergies = new ArrayList<>(); // Danh sách dị ứng

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "chronic_conditions", joinColumns = @JoinColumn(name = "medical_record_id"))
    @Column(name = "condition_name")
    private List<String> chronicConditions = new ArrayList<>(); // Bệnh mãn tính

    // Tiền sử điều trị (có thể là một Entity riêng nếu cần chi tiết hơn)
    private String pastMedicalHistory; // Ví dụ: "Đã từng phẫu thuật ruột thừa năm 2020"

    private Double visionLeft; // Thị lực mắt trái
    private Double visionRight; // Thị lực mắt phải
    private String hearingStatus; // Tình trạng thính lực (ví dụ: "Bình thường", "Giảm nhẹ")

    private LocalDateTime lastUpdated;

    @PreUpdate
    @PrePersist
    public void setLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }

    // Constructors
    public MedicalRecord() {}

    public MedicalRecord(Student student, List<String> allergies, List<String> chronicConditions, String pastMedicalHistory, Double visionLeft, Double visionRight, String hearingStatus) {
        this.student = student;
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
        this.pastMedicalHistory = pastMedicalHistory;
        this.visionLeft = visionLeft;
        this.visionRight = visionRight;
        this.hearingStatus = hearingStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(List<String> chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public Double getVisionLeft() {
        return visionLeft;
    }

    public void setVisionLeft(Double visionLeft) {
        this.visionLeft = visionLeft;
    }

    public Double getVisionRight() {
        return visionRight;
    }

    public void setVisionRight(Double visionRight) {
        this.visionRight = visionRight;
    }

    public String getHearingStatus() {
        return hearingStatus;
    }

    public void setHearingStatus(String hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    // Setter cho lastUpdated (có thể không cần nếu dùng @PrePersist/@PreUpdate)
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}