package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.*;
import com.susanne.Susanne_eindopdrachtVA.repository.FileRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.HomeworkAssignmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkAssignmentService {

    private final HomeworkAssignmentRepository homeworkAssignmentRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final FileRepository fileRepository;

    public HomeworkAssignmentService(HomeworkAssignmentRepository homeWorkAssignmentRepository, FileRepository fileRepository, GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.homeWorkAssignmentRepository = homeWorkAssignmentRepository;
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
        //toevoegen van file
        homeworkAssignment.setGroup(group);
        homeWorkAssignmentRepository.save(homeworkAssignment);
        return modelMapper.map(homeworkAssignment, HomeworkAssignmentOutputDto.class);
    }


    public void assignFileToHomeworkAssignment(String name, Long homeworkAssignmentId) {
        Optional<HomeworkAssignment> optionalHomeworkAssignment = homeworkAssignmentRepository.findById(homeworkAssignmentId);
        Optional<FileUploadResponse> fileUploadResponse = fileRepository.findByFileName(name);
        if (optionalHomeworkAssignment.isPresent() && fileUploadResponse.isPresent()) {
            FileUploadResponse document = fileUploadResponse.get();
            HomeworkAssignment homeworkAssignment = optionalHomeworkAssignment.get();
            homeworkAssignment.setFile(document);
            homeworkAssignmentRepository.save(homeworkAssignment);
        }
    }
}


