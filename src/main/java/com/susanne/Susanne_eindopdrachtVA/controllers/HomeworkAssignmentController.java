package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.services.HomeworkAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/homeworkassignments")
public class HomeworkAssignmentController {

    private final HomeworkAssignmentService homeworkAssignmentService;

    private final GroupRepository groupRepository;

    public HomeworkAssignmentController(HomeworkAssignmentService homeworkAssignmentService, GroupRepository groupRepository) {
        this.homeworkAssignmentService = homeworkAssignmentService;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<List<HomeworkAssignmentOutputDto>> getAssignmentsByGroupId(@PathVariable Long id) {
        List<HomeworkAssignmentOutputDto> homeworkAssignmentOutputDtos = homeworkAssignmentService.getAssignmentsByGroupId(id);
        return ResponseEntity.ok(homeworkAssignmentOutputDtos);
    }


    @PostMapping("/admin/groups/{id}")
    public ResponseEntity<Object> createAndAssignAssignmentToGroup (@Valid @PathVariable Long id, @RequestBody HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        Group group = optionalGroup.get();
        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = homeworkAssignmentService.createAndAssignAssignmentToGroup(group, homeworkAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + homeworkAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(homeworkAssignmentOutputDto);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<HomeworkAssignmentOutputDto> updateHomeworkAssignmentAssignment(@PathVariable Long id, @Valid @RequestBody HomeworkAssignmentInputDto upHomeworkAssignment, BindingResult br) {
        if (br.hasErrors()) {
            String errorHomeworkAssignment = "Fout bij het verwerken van de request";
            throw new BadRequestException(errorHomeworkAssignment);
        }
        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = HomeworkAssignmentService.updateHomeworkAssignment(id, upHomeworkAssignment);
        return ResponseEntity.ok().body(homeworkAssignmentOutputDto);
    }
 }
