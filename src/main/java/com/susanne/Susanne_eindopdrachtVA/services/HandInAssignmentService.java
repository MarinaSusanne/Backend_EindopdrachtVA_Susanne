package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.FileNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.*;
import com.susanne.Susanne_eindopdrachtVA.repository.FileRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.HandInAssignmentRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HandInAssignmentService {

    private final HandInAssignmentRepository handInAssignmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileRepository fileRepository;


    public HandInAssignmentService(HandInAssignmentRepository handInAssignmentRepository, ModelMapper modelMapper, UserRepository userRepository, FileRepository fileRepository) {
        this.handInAssignmentRepository = handInAssignmentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public List<HandInAssignmentOutputDto> getAssignmentsByUserId(Long userId) {
        Iterable<HandInAssignment> handInAssignments = handInAssignmentRepository.findByUserId(userId);
        List<HandInAssignmentOutputDto> handInAssignmentOutputDtos = new ArrayList<>();
        for (HandInAssignment h : handInAssignments) {
            HandInAssignmentOutputDto hODto = modelMapper.map(h, HandInAssignmentOutputDto.class);
            handInAssignmentOutputDtos.add(hODto);
        }
        return handInAssignmentOutputDtos;
    }

    public HandInAssignmentOutputDto handInAssignmentByUser(Long userId, HandInAssignmentInputDto handInAssignmentInputDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        HandInAssignment handInAssignment = modelMapper.map(handInAssignmentInputDto, HandInAssignment.class);
        handInAssignment.setUser(user);
        handInAssignment.setSendDate(LocalDate.now());
        handInAssignmentRepository.save(handInAssignment);
        HandInAssignmentOutputDto hidto = modelMapper.map(handInAssignment, HandInAssignmentOutputDto.class);
        hidto.setAuthor(UserMapper.userToUserLeanDto(user));
        return hidto;
    }

    public HandInAssignmentOutputDto assignFileToHandInAssignment(String name, Long handInAssignmentId) throws FileNotFoundException {
        Optional<HandInAssignment> optionalHandInAssignment = handInAssignmentRepository.findById(handInAssignmentId);
        if (optionalHandInAssignment.isEmpty()) {
            throw new RecordNotFoundException("No Hand-In assignment found");
        }
        Optional<FileUploadResponse> fileUploadResponse = fileRepository.findByFileName(name);
        if (fileUploadResponse.isPresent()) {
            FileUploadResponse document = fileUploadResponse.get();
            HandInAssignment handInAssignment = optionalHandInAssignment.get();
            handInAssignment.setFile(document);
            handInAssignmentRepository.save(handInAssignment);
            HandInAssignmentOutputDto hidto = modelMapper.map(handInAssignment, HandInAssignmentOutputDto.class);
            hidto.setFileName(document.getFileName());
            return hidto;
        } else {
            throw new FileNotFoundException("file not found");
        }
    }
}





