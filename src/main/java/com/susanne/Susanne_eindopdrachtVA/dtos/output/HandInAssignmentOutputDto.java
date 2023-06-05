package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HandInAssignmentOutputDto {

    private Long id;

    private String assignmentName;

    private String info;

    private LocalDate sendDate;

    private UserLeanOutputDto author;

    private String fileName;

}
