package com.example.demo.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Parent;

public interface IParentRepository extends JpaRepository<Parent, Long>{

    // cso them ds hoc sinh trong phu huynh (vi lazy)
    @Query("SELECT p FROM Parent p LEFT JOIN FETCH p.children WHERE p.id = :id")
    Optional<Parent> findParentByIDWithChildren(@Param("id") Long id);


}
