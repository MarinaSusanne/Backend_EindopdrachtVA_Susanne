package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        MessageOutputDto mdto = transferEntityToDto(m);
        messageOutputDtos.add(mdto);
        }
        return messageOutputDtos;
    }

    public MessageOutputDto getOneMessageById(Long id){
        Message message = messageRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Message not found"));
        return transferEntityToDto(message);
    }

    //TODO: eventueel nog een exceptions gooien als er geen messages zijn?

    public MessageOutputDto createMessage(MessageInputDto inputDto){
        Message message = transferDtoToEntity(inputDto);
        message.setSubmitDate(LocalDateTime.now());
        messageRepository.save(message);
        return transferEntityToDto(message);
    }

    public void deleteMessage(@RequestBody Long id) {
        messageRepository.deleteById(id);
    }

    public MessageOutputDto updateMessage(Long id, MessageInputDto upMessage){
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()){
            Message message = messageOptional.get();
            message.setContent(upMessage.getContent());
            message.setSubmitDate(LocalDateTime.now());
            Message updatedMessage = messageRepository.save(message);
            return transferEntityToDto(updatedMessage);

        }
        else{
            throw new RecordNotFoundException("No message found!");
        }
    }

    public Message transferDtoToEntity (MessageInputDto inputDto){
        Message message = new Message();
        message.setContent(inputDto.getContent());
        message.setSubmitDate(inputDto.getSubmitDate());
        return message;
    }

    public MessageOutputDto transferEntityToDto (Message message){
        MessageOutputDto outputDto = new MessageOutputDto();
        outputDto.setId(message.getId());
        outputDto.setContent(message.getContent());
        outputDto.setSubmitDate(message.getSubmitDate());
        return outputDto;
    }
}
