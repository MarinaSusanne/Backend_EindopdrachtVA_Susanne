package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.User;
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
    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public List<MessageOutputDto> getAllMessages(){
        Iterable<Message> messages = messageRepository.findAll();
        List <MessageOutputDto> messageOutputDtos = new ArrayList<>();
        for (Message m : messages) {
        MessageOutputDto mdto = messageMapper.messageToMessageDto(m);
        messageOutputDtos.add(mdto);
        }
        return messageOutputDtos;
    }

    public MessageOutputDto getOneMessageById(Long id){
        Message message = messageRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Message not found"));
        return messageMapper.messageToMessageDto(message);
    }

    public List<MessageOutputDto> getMessagesByUser(Long userId) {
        Optional<List<Message>> optionalMessages = messageRepository.findByUser_Id(userId);
        if (optionalMessages.isPresent()) {
            List<Message> messages = optionalMessages.get();
            List<MessageOutputDto> messageOutputDtos = new ArrayList<>();
            for (Message m : messages) {
                MessageOutputDto mdto = messageMapper.messageToMessageDto(m);
                messageOutputDtos.add(mdto);
            }
            return messageOutputDtos;
        } else {
            throw new RecordNotFoundException("User not found");
        }
    }

    public MessageOutputDto createAndAssignMessage(User user, MessageInputDto inputDto){
        Message message = messageMapper.messageDtoToMessage(inputDto);
        message.setSubmitDate(LocalDateTime.now());
        message.setUser(user);
        messageRepository.save(message);
        return messageMapper.messageToMessageDto(message);
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
            return messageMapper.messageToMessageDto(updatedMessage);

        }
        else{
            throw new RecordNotFoundException("No message found!");
        }
    }


}
