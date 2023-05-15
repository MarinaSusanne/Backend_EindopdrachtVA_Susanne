package com.susanne.Susanne_eindopdrachtVA.controllers;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.services.HandInAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


//@CrossOrigin
@RestController
@RequestMapping("/handinassignments")
public class HandInAssignmentController {

    private final HandInAssignmentService handInAssignmentService;



    public HandInAssignmentController(HandInAssignmentService handInAssignmentService) {
        this.handInAssignmentService = handInAssignmentService;

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<HandInAssignmentOutputDto>> getAssignmentsByUserId(@PathVariable Long userId) {
        List<HandInAssignmentOutputDto> handInAssignmentOutputDtos = handInAssignmentService.getAssignmentsByUserId(userId);
        return ResponseEntity.ok(handInAssignmentOutputDtos);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<Object> handInAssignmentByUser (@Valid @PathVariable Long userId, @RequestBody HandInAssignmentInputDto handinAssignmentInputDto) {
        HandInAssignmentOutputDto handInAssignmentOutputDto = handInAssignmentService.handInAssignmentByUser(userId, handinAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + handInAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(handInAssignmentOutputDto);
    }

}
