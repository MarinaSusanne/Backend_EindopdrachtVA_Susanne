package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeworkAssignmentService {


    public static HomeworkAssignmentOutputDto updateHomeworkAssignment(Long id, HomeworkAssignmentInputDto upHomeworkAssignment) {
        return null;
    }

    public List<HomeworkAssignmentOutputDto> getAssignmentsByGroupId(Long groupId) {
        return null;

    }

    public HomeworkAssignmentOutputDto createAndAssignAssignmentToGroup(Long groupId, HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
//        Optional<Group> optionalGroup = groupRepository.findById(groupId);
//        if (optionalGroup.isEmpty()) {
//            return ResponseEntity.badRequest().body("User does not exist");
//        }
//        Group group = optionalGroup.get();
        return null;
    }
}
