package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.HandInAssignment;
import com.susanne.Susanne_eindopdrachtVA.model.HomeworkAssignment;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.HomeWorkAssignmentRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkAssignmentService {

    private final HomeWorkAssignmentRepository homeWorkAssignmentRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public HomeworkAssignmentService(HomeWorkAssignmentRepository homeWorkAssignmentRepository, GroupRepository groupRepository, ModelMapper modelMapper) {
    this.groupRepository = groupRepository;
    this.homeWorkAssignmentRepository = homeWorkAssignmentRepository;
    this.modelMapper = modelMapper;
    }




    public List<HomeworkAssignmentOutputDto> getAssignmentsByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RecordNotFoundException("Group not found"));
        Iterable<HomeworkAssignment> homeworkAssignments = group.getHomeworkAssignments();
        List <HomeworkAssignmentOutputDto> homeworkAssignmentOutputDtos = new ArrayList<>();
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

}



