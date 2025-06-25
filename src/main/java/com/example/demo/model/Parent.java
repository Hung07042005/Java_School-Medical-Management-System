// src/main/java/com/example/demo/model/Parent.java
package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
// import jakarta.persistence.OneToMany; // Không cần import nếu không dùng
// import java.util.HashSet; // Không cần import nếu không dùng
// import java.util.Set; // Không cần import nếu không dùng

@Entity
@Table(name = "parents")
public class Parent extends User {

    private String relationshipToStudent;

    // XÓA HOẶC COMMENT HOÀN TOÀN CÁC DÒNG NÀY ĐI
    // @OneToMany
    // TODO: Cần thêm @JoinColumn hoặc @JoinTable để định nghĩa mối quan hệ rõ ràng
    // Hoặc xử lý mối quan hệ từ phía Student (nhiều Student có thể có cùng Parent)
    // Để đơn giản tạm thời không ánh xạ trực tiếp từ đây
    // Trong thực tế, mối quan hệ này sẽ phức tạp hơn (nhiều-nhiều hoặc nhiều-một từ phía Student)
    // private Set<Student> children = new HashSet<>();


    // Constructors
    public Parent() {}

    public Parent(String username, String password, String fullName, String email, String phoneNumber, String relationshipToStudent) {
        super(username, password, fullName, email, phoneNumber);
        this.relationshipToStudent = relationshipToStudent;
    }

    // Getters and Setters
    public String getRelationshipToStudent() {
        return relationshipToStudent;
    }

    public void setRelationshipToStudent(String relationshipToStudent) {
        this.relationshipToStudent = relationshipToStudent;
    }
}