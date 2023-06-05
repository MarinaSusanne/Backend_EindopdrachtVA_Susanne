package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import com.susanne.Susanne_eindopdrachtVA.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HomeworkAssignmentOutputDto {

    private Long id;

    private String assignmentName;

    private String info;

    private LocalDate sendDate;

    private String fileName;
}
