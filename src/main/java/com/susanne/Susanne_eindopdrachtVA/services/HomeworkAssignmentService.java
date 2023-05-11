package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.HomeworkAssignmentInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.HomeworkAssignmentOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HomeworkAssignmentService {


    public static HomeworkAssignmentOutputDto updateHomeworkAssignment(Long id, HomeworkAssignmentInputDto upHomeworkAssignment) {
        return null;
    }

    public List<HomeworkAssignmentOutputDto> getAssignmentsByGroupId(Long groupId) {
        return null;

    }

    public HomeworkAssignmentOutputDto createAndAssignAssignmentToGroup(Group group, HomeworkAssignmentInputDto homeworkAssignmentInputDto) {
        return null;
    }
}
