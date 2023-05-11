package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class HandInAssignmentService {


    public List<HandInAssignmentOutputDto> getAssignmentsByUserId(Long userId) {
        return null;
    }

    public HandInAssignmentOutputDto handInAssignmentByUser(HandInAssignmentInputDto handinAssignmentInputDto) {
        return null;
    }
}