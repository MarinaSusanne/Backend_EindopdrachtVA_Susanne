package com.susanne.Susanne_eindopdrachtVA.services;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageOutputDto> getAllMessages() {
        Iterable<Message> messages = messageRepository.findAll();
        List<MessageOutputDto> messageOutputDtos = new ArrayList<>();
        for (Message m : messages) {
            User user = m.getUser();
            UserLeanOutputDto udto = UserMapper.userToUserLeanDto(user);
            MessageOutputDto mdto = MessageMapper.messageToMessageDtoWithLeanUser(m, udto);
            messageOutputDtos.add(mdto);
        }
        return messageOutputDtos;
    }

    public MessageOutputDto createAndAssignMessage(Long userId, MessageInputDto inputDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Message message = MessageMapper.messageDtoToMessage(inputDto);
        message.setSubmitDate(LocalDateTime.now());
        message.setUser(user);
        MessageBoard messageBoard = user.getGroup().getMessageBoard();
        message.setMessageBoard(messageBoard);
        messageRepository.save(message);
        UserLeanOutputDto userLeanOutputDto = UserMapper.userToUserLeanDto(user);
        return MessageMapper.messageToMessageDtoWithLeanUser(message, userLeanOutputDto);
    }


    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RecordNotFoundException("Bericht niet gevonden met ID: " + id);
        }
        messageRepository.deleteById(id);
    }

    public MessageOutputDto updateMessage(Long id, MessageInputDto upMessage) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setContent(upMessage.getContent());
            message.setSubmitDate(LocalDateTime.now());
            Message updatedMessage = messageRepository.save(message);
            return MessageMapper.messageToMessageDto(updatedMessage);

        } else {
            throw new RecordNotFoundException("No message found!");
        }
    }
}

