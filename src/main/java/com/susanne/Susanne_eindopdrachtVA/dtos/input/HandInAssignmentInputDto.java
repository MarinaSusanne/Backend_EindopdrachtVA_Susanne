package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandInAssignmentInputDto {

    @NotNull
    private String assignmentName;

    @NotNull
    private String info;

}
