
package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parents")
public class Parent extends User {


    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> children = new ArrayList<>();

    // Constructors
    public Parent() {
        super();
    }

    public Parent(String username, String password, String fullName, String email, String phoneNumber) {
        super(username, password, fullName, email, phoneNumber);
    }

    // Getters and Setters
    public List<Student> getChildren() {
        // Optional: remove duplicates if needed (just in case)
        return new ArrayList<>(children.stream().distinct().toList());
    }

    public void setChildren(List<Student> children) {
        this.children = children;
    }

}