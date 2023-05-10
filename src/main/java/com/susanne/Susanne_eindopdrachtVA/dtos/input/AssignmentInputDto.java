package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import jakarta.validation.constraints.NotNull;

public class AssignmentInputDto {

    @NotNull
    private String name;

    @NotNull
    private String info;

}
