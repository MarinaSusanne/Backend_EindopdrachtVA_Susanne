package com.susanne.Susanne_eindopdrachtVA.mappers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public static Message messageDtoToMessage(MessageInputDto inputDto) {
        Message message = new Message();
        message.setContent(inputDto.getContent());
        message.setSubmitDate(inputDto.getSubmitDate());
        return message;
    }

    public static MessageOutputDto messageToMessageDto(Message message) {
        MessageOutputDto outputDto = new MessageOutputDto();
        outputDto.setId(message.getId());
        outputDto.setContent(message.getContent());
        outputDto.setSubmitDate(message.getSubmitDate());
        return outputDto;
    }

    public static MessageOutputDto messageToMessageDtoWithLeanUser(Message message, UserLeanOutputDto userLeanOutputDto) {
        MessageOutputDto outputDto = new MessageOutputDto();
        outputDto.setId(message.getId());
        outputDto.setContent(message.getContent());
        outputDto.setSubmitDate(message.getSubmitDate());
        outputDto.setUserLeanOutputDto(userLeanOutputDto);
        return outputDto;
    }
}
