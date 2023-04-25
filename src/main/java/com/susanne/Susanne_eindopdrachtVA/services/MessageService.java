package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageOutputDto> getAllMessages(){
        Iterable<Message> messages = messageRepository.findAll();
        List <MessageOutputDto> messageOutputDtos = new ArrayList<>();
        for (Message m : messages) {
        MessageOutputDto mdto = new MessageOutputDto();
        mdto = transferEntityToDto(m);
        messageOutputDtos.add(mdto);
        }
        return messageOutputDtos;
    }

    //TODO: eventueel nog een exceptions gooien als er geen messages zijn?

    public MessageOutputDto createMessage(MessageInputDto inputDto){
        Message message = transferDtoToEntity(inputDto);
        messageRepository.save(message);
        return transferEntityToDto(message);
    }

    public Message transferDtoToEntity (MessageInputDto inputDto){
        Message message = new Message();
        message.setContent(inputDto.getContent());
        message.setSubmitDate(inputDto.getSubmitDate());
        return message;
    }

    public MessageOutputDto transferEntityToDto (Message message){
        MessageOutputDto outputDto = new MessageOutputDto();
        outputDto.setContent(message.getContent());
        outputDto.setSubmitDate(message.getSubmitDate());
        return outputDto;
    }
}
