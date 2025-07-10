package com.example.demo.repository;

import com.example.demo.model.Vaccine;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccineRepository extends JpaRepository<Vaccine, Long> {
    @EntityGraph(attributePaths = "participants")
    List<Vaccine> findAll();

}
