package com.example.demo.service;

import com.example.demo.model.MedicalRecord;
import com.example.demo.model.Student;
import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.dto.MedicalRecordDTO; // Đảm bảo dòng này có và đúng
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Optional<MedicalRecord> getMedicalRecordByStudentId(Long studentId) {
        return medicalRecordRepository.findByStudentId(studentId);
    }

    /**
     * Phương thức lưu hoặc cập nhật hồ sơ sức khỏe.
     * Nếu medicalRecordDTO có ID, sẽ cố gắng tìm và cập nhật bản ghi hiện có.
     * Nếu medicalRecordDTO không có ID, sẽ tạo bản ghi mới.
     * @param medicalRecordDTO DTO chứa thông tin hồ sơ sức khỏe.
     * @return Đối tượng MedicalRecord đã được lưu vào DB.
     */
    @Transactional
    public MedicalRecord saveMedicalRecordFromDTO(MedicalRecordDTO medicalRecordDTO) {
        if (medicalRecordDTO.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID is required for medical record.");
        }

        Student student = studentRepository.findById(medicalRecordDTO.getStudentId())
                                         .orElseThrow(() -> new RuntimeException("Student not found with ID: " + medicalRecordDTO.getStudentId()));

        MedicalRecord medicalRecord;
        if (medicalRecordDTO.getId() != null) { // Kiểm tra: Nếu có ID -> Đang cập nhật
            medicalRecord = medicalRecordRepository.findById(medicalRecordDTO.getId())
                                                 .orElseThrow(() -> new RuntimeException("Medical Record not found with ID: " + medicalRecordDTO.getId()));
            // Đảm bảo MedicalRecord này thuộc về đúng Student
            if (!medicalRecord.getStudent().getId().equals(student.getId())) {
                throw new IllegalArgumentException("Medical Record ID does not match Student ID.");
            }
        } else { // Kiểm tra: Nếu không có ID -> Đang tạo mới
            // Kiểm tra xem học sinh đã có hồ sơ y tế chưa (một học sinh chỉ có một hồ sơ)
            if (medicalRecordRepository.findByStudentId(student.getId()).isPresent()) {
                throw new IllegalArgumentException("Student already has a medical record.");
            }
            medicalRecord = new MedicalRecord();
            medicalRecord.setStudent(student);
        }

        // Cập nhật các trường từ DTO vào Entity
        medicalRecord.setAllergies(medicalRecordDTO.getAllergies());
        medicalRecord.setChronicConditions(medicalRecordDTO.getChronicConditions());
        medicalRecord.setPastMedicalHistory(medicalRecordDTO.getPastMedicalHistory());
        medicalRecord.setVisionLeft(medicalRecordDTO.getVisionLeft());
        medicalRecord.setVisionRight(medicalRecordDTO.getVisionRight());
        medicalRecord.setHearingStatus(medicalRecordDTO.getHearingStatus());

        return medicalRecordRepository.save(medicalRecord);
    }

    public Optional<MedicalRecord> getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }
}