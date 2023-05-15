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
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

    private final MessageBoardRepository messageBoardRepository;

    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper, MessageBoardRepository messageBoardRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messageBoardRepository = messageBoardRepository;
        this.userRepository = userRepository;
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

//     public List<MessageOutputDto> getMessagesByUser(Long userId) {
//         Optional<List<Message>> optionalMessages = messageRepository.findByUser_Id(userId);
//         if (optionalMessages.isPresent()) {
//             List<Message> messages = optionalMessages.get();
//             List<MessageOutputDto> messageOutputDtos = new ArrayList<>();
//             for (Message m : messages) {
//                 MessageOutputDto mdto = messageMapper.messageToMessageDto(m);
//                 messageOutputDtos.add(mdto);
//             }
//             return messageOutputDtos;
//         }
//         throw new RecordNotFoundException("User not found");
//     }

    public MessageOutputDto createAndAssignMessage(Long userId, MessageInputDto inputDto){
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        Message message = messageMapper.messageDtoToMessage(inputDto);
        message.setSubmitDate(LocalDateTime.now());
        message.setUser(user);
        MessageBoard messageBoard = user.getGroup().getMessageBoard();
        message.setMessageBoard(messageBoard);
        messageBoard.addMessagetoMessageList(message);
        messageBoardRepository.save(messageBoard);
        messageRepository.save(message);
        UserLeanOutputDto userLeanOutputDto = UserMapper.userToUserLeanDto(user);
        return messageMapper.messageToMessageDtoWithLeanUser(message, userLeanOutputDto);
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
