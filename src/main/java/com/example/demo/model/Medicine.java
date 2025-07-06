package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private String name;
    private String type;
    private String unit;
    private int quantity;
    private LocalDate expiryDate;
    private String note;

    // ✅ Thêm cột để y tá xác nhận đã cho uống
    private Boolean isGiven = false;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Boolean getIsGiven() { return isGiven; }
    public void setIsGiven(Boolean isGiven) { this.isGiven = isGiven; }

    // Hoặc nếu bạn muốn đổi tên cho đẹp hơn:
    public Boolean getGiven() { return isGiven; }
    public void setGiven(Boolean given) { this.isGiven = given; }
}
