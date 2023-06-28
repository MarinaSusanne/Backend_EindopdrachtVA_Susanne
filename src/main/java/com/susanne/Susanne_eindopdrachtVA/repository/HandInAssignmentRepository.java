package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.HandInAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HandInAssignmentRepository extends JpaRepository<HandInAssignment, Long> {
    public List<HandInAssignment> findByUserId(Long id);
}
