package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.HomeworkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandInAssignmentRepository extends JpaRepository <HomeworkAssignment, Long> {
}
