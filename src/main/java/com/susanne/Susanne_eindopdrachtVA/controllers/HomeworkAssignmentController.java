package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import com.susanne.Susanne_eindopdrachtVA.services.HomeworkAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/homeworkassignments")
public class HomeworkAssignmentController {

    private final HomeworkAssignmentService homeworkAssignmentService;
    private final FileController fileController;


    public HomeworkAssignmentController(HomeworkAssignmentService homeworkAssignmentService, FileController fileController) {
        this.homeworkAssignmentService = homeworkAssignmentService;
        this.fileController = fileController;
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<HomeworkAssignmentOutputDto>> getAssignmentsByGroupId(@PathVariable Long groupId) {
        List<HomeworkAssignmentOutputDto> homeworkAssignmentOutputDtos = homeworkAssignmentService.getAssignmentsByGroupId(groupId);
        return ResponseEntity.ok(homeworkAssignmentOutputDtos);
    }


    @PostMapping("/admin/groups/{groupId}")
    public ResponseEntity<Object> createAndAssignAssignmentToGroup(@PathVariable Long groupId, @Valid @RequestBody HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = homeworkAssignmentService.createAndAssignAssignmentToGroup(groupId, homeworkAssignmentInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + homeworkAssignmentOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(homeworkAssignmentOutputDto);
    }

    @PostMapping("/{id}/file")
    public ResponseEntity  <HomeworkAssignmentOutputDto> assignFileToHomeWorkAssignment (@PathVariable("id") Long homeworkAssignmentId,
                                                 @RequestBody MultipartFile file) {

        FileUploadResponse document = fileController.singleFileUpload(file);
       HomeworkAssignmentOutputDto hwdto = homeworkAssignmentService.assignFileToHomeworkAssignment(document.getFileName(), homeworkAssignmentId);
        return ResponseEntity.ok(hwdto);
    }
}

//    @PutMapping("/admin/{id}")
//    public ResponseEntity<HomeworkAssignmentOutputDto> updateHomeworkAssignment(@PathVariable Long id, @Valid @RequestBody HomeworkAssignmentInputDto upHomeworkAssignment, BindingResult br) {
//        if (br.hasErrors()) {
//            String errorHomeworkAssignment = "Fout bij het verwerken van de request";
//            throw new BadRequestException(errorHomeworkAssignment);
//        }
//        HomeworkAssignmentOutputDto homeworkAssignmentOutputDto = HomeworkAssignmentService.updateHomeworkAssignment(id, upHomeworkAssignment);
//        return ResponseEntity.ok().body(homeworkAssignmentOutputDto);
//    }
// }
