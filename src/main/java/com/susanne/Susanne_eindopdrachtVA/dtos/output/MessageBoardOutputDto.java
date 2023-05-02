package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageBoardOutputDto {

    private Long id;
    private List<Message> messages;
    private Group group;

}
