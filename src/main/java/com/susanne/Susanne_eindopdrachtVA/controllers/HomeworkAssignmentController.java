package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.services.HomeworkAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


//@CrossOrigin
@RestController
@RequestMapping("/homeworkassignments")
public class HomeworkAssignmentController {

    private final HomeworkAssignmentService homeworkAssignmentService;


    public HomeworkAssignmentController(HomeworkAssignmentService homeworkAssignmentService) {
        this.homeworkAssignmentService = homeworkAssignmentService;
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<HomeworkAssignmentOutputDto>> getAssignmentsByGroupId(@PathVariable Long groupId) {
        List<HomeworkAssignmentOutputDto> homeworkAssignmentOutputDtos = homeworkAssignmentService.getAssignmentsByGroupId(groupId);
        return ResponseEntity.ok(homeworkAssignmentOutputDtos);
    }


    @PostMapping("/admin/groups/{groupId}")
    public ResponseEntity<Object> createAndAssignAssignmentToGroup (@Valid @PathVariable Long groupId, @RequestBody HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = homeworkAssignmentService.createAndAssignAssignmentToGroup(groupId, homeworkAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + homeworkAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(homeworkAssignmentOutputDto);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<HomeworkAssignmentOutputDto> updateHomeworkAssignment(@PathVariable Long id, @Valid @RequestBody HomeworkAssignmentInputDto upHomeworkAssignment, BindingResult br) {
        if (br.hasErrors()) {
            String errorHomeworkAssignment = "Fout bij het verwerken van de request";
            throw new BadRequestException(errorHomeworkAssignment);
        }
        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = HomeworkAssignmentService.updateHomeworkAssignment(id, upHomeworkAssignment);
        return ResponseEntity.ok().body(homeworkAssignmentOutputDto);
    }
 }
