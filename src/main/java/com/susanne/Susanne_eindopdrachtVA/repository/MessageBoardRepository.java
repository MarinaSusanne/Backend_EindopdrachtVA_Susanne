package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageBoardRepository extends JpaRepository <MessageBoard, Long> {
}
