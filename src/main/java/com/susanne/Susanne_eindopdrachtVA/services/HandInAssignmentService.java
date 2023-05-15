package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class HandInAssignmentService {


    public List<HandInAssignmentOutputDto> getAssignmentsByUserId(Long userId) {
        return null;
    }

    public HandInAssignmentOutputDto handInAssignmentByUser(Long userId, HandInAssignmentInputDto handinAssignmentInputDto) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.badRequest().body("User does not exist");
//        }
//        User user = optionalUser.get();

        return null;
    }
}
