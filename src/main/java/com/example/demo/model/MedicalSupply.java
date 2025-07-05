package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Tên thuốc / vật tư
    private String type;        // "Thuốc" hoặc "Vật tư"
    private String unit;        // Đơn vị tính
    private int quantity;       // Số lượng
    private LocalDate expiryDate; // Hạn sử dụng (nullable)
    private String note;        // Ghi chú
}
