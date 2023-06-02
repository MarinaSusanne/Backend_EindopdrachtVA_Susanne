package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.HandInAssignment;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.HandInAssignmentRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HandInAssignmentService {

    private final HandInAssignmentRepository handInAssignmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public HandInAssignmentService (HandInAssignmentRepository handInAssignmentRepository, ModelMapper modelMapper, UserRepository userRepository){
        this.handInAssignmentRepository = handInAssignmentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<HandInAssignmentOutputDto> getAssignmentsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Iterable<HandInAssignment> handInAssignments = user.getHandInAssignments();
        List <HandInAssignmentOutputDto> handInAssignmentOutputDtos = new ArrayList<>();
        for (HandInAssignment h : handInAssignments) {
            HandInAssignmentOutputDto hODto = modelMapper.map(h, HandInAssignmentOutputDto.class);
            handInAssignmentOutputDtos.add(hODto);
        }
        return handInAssignmentOutputDtos;
    }


    public HandInAssignmentOutputDto handInAssignmentByUser(Long userId, HandInAssignmentInputDto handInAssignmentInputDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        HandInAssignment handInAssignment = modelMapper.map(handInAssignmentInputDto, HandInAssignment.class);
        //oplaan van file
        handInAssignmentRepository.save(handInAssignment);
        return modelMapper.map(handInAssignment, HandInAssignmentOutputDto.class);
    }
}

