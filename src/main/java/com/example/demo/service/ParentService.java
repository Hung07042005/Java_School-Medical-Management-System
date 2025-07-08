package com.example.demo.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Parent;
import com.example.demo.model.Student;
import com.example.demo.repository.IParentRepository;
import com.example.demo.repository.StudentRepository;


@Service
public class ParentService {
    @Autowired
    private IParentRepository parentRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Parent saveParent(Parent parent){
        return parentRepository.save(parent);
    }


    public Optional<Parent> getParentByIDWithChildren(Long parent_ID){
        return parentRepository.findParentByIDWithChildren(parent_ID);
    }

    public void deleteParentByID(Long parent_ID){
        Parent parent = parentRepository.findParentByIDWithChildren(parent_ID)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh"));

        for (Student student : parent.getChildren()) {
            student.setParent(null); 
            studentRepository.save(student); 
        }
        parentRepository.delete(parent);
    }
}