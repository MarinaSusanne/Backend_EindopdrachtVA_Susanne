package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.services.HandInAssignmentService;
import com.susanne.Susanne_eindopdrachtVA.services.HomeworkAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/handinassignments")
public class HandInAssignmentController {

    private final HandInAssignmentService handInAssignmentService;

    private final UserRepository userRepository;


    public HandInAssignmentController(HandInAssignmentService handInAssignmentService, UserRepository userRepository) {
        this.handInAssignmentService = handInAssignmentService;
        this.userRepository = userRepository;

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<HandInAssignmentOutputDto>> getAssignmentsByUserId(@PathVariable Long userId) {
        List<HandInAssignmentOutputDto> handInAssignmentOutputDtos = handInAssignmentService.getAssignmentsByUserId(userId);
        return ResponseEntity.ok(handInAssignmentOutputDtos);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<Object> handInAssignmentByUser (@Valid @PathVariable Long userId, @RequestBody HandInAssignmentInputDto handinAssignmentInputDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        User user = optionalUser.get();
        HandInAssignmentOutputDto handInAssignmentOutputDto = handInAssignmentService.handInAssignmentByUser(handinAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + handInAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(handInAssignmentOutputDto);
    }

}
