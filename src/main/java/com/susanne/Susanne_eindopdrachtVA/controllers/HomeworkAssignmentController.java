package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.AssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.AssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.services.HomeworkAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/assignments")
public class HomeworkAssignmentController {

    private final HomeworkAssignmentService homeworkAssignmentService;

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    public HomeworkAssignmentController(HomeworkAssignmentService homeworkAssignmentService, UserRepository userRepository, GroupRepository groupRepository) {
        this.homeworkAssignmentService = homeworkAssignmentService;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<List<AssignmentOutputDto>> getAssignmentsByGroupId(@PathVariable Long id) {
        List<AssignmentOutputDto> assignmentOutputDtos = homeworkAssignmentService.getAssignmentsByGroupId(id);
        return ResponseEntity.ok(assignmentOutputDtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<AssignmentOutputDto>> getAssignmentsByUserId(@PathVariable Long id) {
        List<AssignmentOutputDto> assignmentOutputDtos = homeworkAssignmentService.getAssignmentsByUserId(id);
        return ResponseEntity.ok(assignmentOutputDtos);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> handInAssignment (@Valid @PathVariable Long id, @RequestBody AssignmentInputDto assignmentInputDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        User user = optionalUser.get();
        AssignmentOutputDto assignmentOutputDto = homeworkAssignmentService.handInAssignment(assignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + assignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(assignmentOutputDto);
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<Object> postAssignmentForGroup (@Valid @PathVariable Long id, @RequestBody AssignmentInputDto assignmentInputDto) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return ResponseEntity.badRequest().body("Group does not exist");
        }
        Group group = optionalGroup.get();
        AssignmentOutputDto assignmentOutputDto = homeworkAssignmentService.postAssignmentForGroup(assignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + assignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(assignmentOutputDto);
    }



}
