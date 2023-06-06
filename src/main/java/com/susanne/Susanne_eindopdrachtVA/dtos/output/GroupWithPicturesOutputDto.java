package com.susanne.Susanne_eindopdrachtVA.dtos.output;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GroupWithPicturesOutputDto {

    private Long id;
    private String groupName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String groupInfo;
    private User admin;
    private List<UserPictureOutputDto> userPictureOutputDto;
    private Long messageBoardId;
}
