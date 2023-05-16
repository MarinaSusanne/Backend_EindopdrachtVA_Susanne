package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageBoardInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageBoardOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageBoardService {

    private final MessageBoardRepository messageBoardRepository;
    private final MessageRepository messageRepository;


    public MessageBoardService(MessageBoardRepository messageBoardRepository, MessageRepository messageRepository){
        this.messageBoardRepository = messageBoardRepository;
        this.messageRepository = messageRepository;
    }

    public List<MessageOutputDto> getMessagesFromBoard(Long id){
        MessageBoard messageBoard = messageBoardRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("MessageBoard not found"));
        Iterable<Message> messages = messageBoard.getMessages();
        List <MessageOutputDto> messageOutputDtos = new ArrayList<>();
        for (Message m : messages) {
            MessageOutputDto mdto = MessageMapper.messageToMessageDto(m);
            messageOutputDtos.add(mdto);
        }
        return messageOutputDtos;
    }

    public MessageBoardOutputDto updateMessageBoard(Long id, MessageBoardInputDto upMessageBoard){
        Optional<MessageBoard> messageBoardOptional = messageBoardRepository.findById(id);
        if (messageBoardOptional.isPresent()){
            MessageBoard messageBoard = messageBoardOptional.get();
            if (upMessageBoard != null) {
                messageBoard.setBoardInfo(upMessageBoard.getBoardInfo());
            }
            MessageBoard updatedMessageBoard = messageBoardRepository.save(messageBoard);
            MessageBoardOutputDto messageBoardOutputDto = new MessageBoardOutputDto();
            messageBoardOutputDto.setId(updatedMessageBoard.getId());
            messageBoardOutputDto.setBoardInfo(updatedMessageBoard.getBoardInfo());
            return messageBoardOutputDto;
        }
        else{
            throw new RecordNotFoundException("No messageBoard found!");
        }
    }


}
