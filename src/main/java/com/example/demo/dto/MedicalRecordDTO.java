// src/main/java/com/example/demo/dto/MedicalRecordDTO.java
package com.example.demo.dto;

import java.util.List;

public class MedicalRecordDTO {
    private Long id; // ID của MedicalRecord (null khi tạo mới)
    private Long studentId; // ID của học sinh mà hồ sơ này thuộc về
    private List<String> allergies;
    private List<String> chronicConditions;
    private String pastMedicalHistory;
    private Double visionLeft;
    private Double visionRight;
    private String hearingStatus;

    // Constructors
    public MedicalRecordDTO() {}

    public MedicalRecordDTO(Long id, Long studentId, List<String> allergies, List<String> chronicConditions, String pastMedicalHistory, Double visionLeft, Double visionRight, String hearingStatus) {
        this.id = id;
        this.studentId = studentId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
}