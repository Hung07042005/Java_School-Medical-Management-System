package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "school_nurses")
public class SchoolNurse extends User { // Kế thừa từ User

    private String employeeId; // Mã nhân viên
    private String licenseNumber; // Số giấy phép y tế

    // Constructors
    public SchoolNurse() {}

    public SchoolNurse(String username, String password, String fullName, String email, String phoneNumber, String employeeId, String licenseNumber) {
        super(username, password, fullName, email, phoneNumber);
        this.employeeId = employeeId;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}