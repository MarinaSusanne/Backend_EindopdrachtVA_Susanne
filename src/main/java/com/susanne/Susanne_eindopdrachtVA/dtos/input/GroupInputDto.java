package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GroupInputDto {

    @NotNull
    private String groupName;

    private LocalDate startDate;

    @Future
    private LocalDate endDate;

    private String groupInfo;

    private User admin;

    @NotNull
    private List<Long> users;

}


