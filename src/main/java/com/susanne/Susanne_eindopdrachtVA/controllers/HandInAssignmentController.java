package com.susanne.Susanne_eindopdrachtVA.controllers;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.HandInAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HandInAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import com.susanne.Susanne_eindopdrachtVA.services.HandInAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/handinassignments")
public class HandInAssignmentController {

    private final HandInAssignmentService handInAssignmentService;
    private final FileController fileController;


    public HandInAssignmentController(HandInAssignmentService handInAssignmentService, FileController fileController) {
        this.handInAssignmentService = handInAssignmentService;
        this.fileController = fileController;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<HandInAssignmentOutputDto>> getAssignmentsByUserId(@PathVariable Long userId) {
        List<HandInAssignmentOutputDto> handInAssignmentOutputDtos = handInAssignmentService.getAssignmentsByUserId(userId);
        return ResponseEntity.ok(handInAssignmentOutputDtos);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<Object> handInAssignmentByUser ( @PathVariable Long userId, @Valid @RequestBody HandInAssignmentInputDto handinAssignmentInputDto) {
        HandInAssignmentOutputDto handInAssignmentOutputDto = handInAssignmentService.handInAssignmentByUser(userId, handinAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + handInAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(handInAssignmentOutputDto);
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<HandInAssignmentOutputDto> assignFileToHandInAssignment (@PathVariable("id") Long handInAssignmentId,
                                              @RequestParam MultipartFile file) {

        FileUploadResponse document = fileController.singleFileUpload(file);
        HandInAssignmentOutputDto hidto =   handInAssignmentService.assignFileToHandInAssignment(document.getFileName(), handInAssignmentId);
     return ResponseEntity.ok(hidto);
    }
}



