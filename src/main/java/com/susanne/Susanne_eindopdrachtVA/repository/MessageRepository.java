package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository < Message, Long> {
    Optional <List<Message>> findByUser_Id(Long userId);
}
