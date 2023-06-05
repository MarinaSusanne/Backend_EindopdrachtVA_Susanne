package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.FileNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.*;
import com.susanne.Susanne_eindopdrachtVA.repository.FileRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.HomeworkAssignmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkAssignmentService {

    private final HomeworkAssignmentRepository homeworkAssignmentRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final FileRepository fileRepository;

    public HomeworkAssignmentService(HomeworkAssignmentRepository homeworkAssignmentRepository, FileRepository fileRepository, GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.homeworkAssignmentRepository = homeworkAssignmentRepository;
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
    }


    public List<HomeworkAssignmentOutputDto> getAssignmentsByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RecordNotFoundException("Group not found"));
        Iterable<HomeworkAssignment> homeworkAssignments = group.getHomeworkAssignments();
        List<HomeworkAssignmentOutputDto> homeworkAssignmentOutputDtos = new ArrayList<>();
        for (HomeworkAssignment h : homeworkAssignments) {
            HomeworkAssignmentOutputDto hwDto = modelMapper.map(h, HomeworkAssignmentOutputDto.class);
            homeworkAssignmentOutputDtos.add(hwDto);
        }
        return homeworkAssignmentOutputDtos;

    }

    public HomeworkAssignmentOutputDto createAndAssignAssignmentToGroup(Long groupId, HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RecordNotFoundException("Group not found"));
        HomeworkAssignment homeworkAssignment = modelMapper.map(homeworkAssignmentInputDto, HomeworkAssignment.class);
        homeworkAssignment.setGroup(group);
        homeworkAssignment.setSendDate(LocalDate.now());
        homeworkAssignmentRepository.save(homeworkAssignment);
        return modelMapper.map(homeworkAssignment, HomeworkAssignmentOutputDto.class);
    }


    public HomeworkAssignmentOutputDto assignFileToHomeworkAssignment(String name, Long homeworkAssignmentId) {
        Optional <HomeworkAssignment> optionalHomeworkAssignment = homeworkAssignmentRepository.findById(homeworkAssignmentId);
        if (optionalHomeworkAssignment.isEmpty()) {
            throw new RecordNotFoundException("No homework assignment found");
        }
        Optional<FileUploadResponse> fileUploadResponse = fileRepository.findByFileName(name);
        if (fileUploadResponse.isPresent()) {
            FileUploadResponse document = fileUploadResponse.get();
            HomeworkAssignment homeworkAssignment = optionalHomeworkAssignment.get();
            homeworkAssignment.setFile(document);
            homeworkAssignmentRepository.save(homeworkAssignment);
            HomeworkAssignmentOutputDto hwdto = modelMapper.map(homeworkAssignment, HomeworkAssignmentOutputDto.class);
            hwdto.setFileName(document.getFileName());
            return hwdto;
        } else {
            throw new FileNotFoundException("file not found");
        }
    }
}


