package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageBoardInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageBoardOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.services.MessageBoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageboards")
public class MessageBoardController {

    private final MessageBoardService messageBoardService;

    public MessageBoardController(MessageBoardService messageBoardService) {
        this.messageBoardService = messageBoardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageOutputDto>> getMessagesFromBoard(@PathVariable Long id) {
        List<MessageOutputDto> messageOutputDtos = messageBoardService.getMessagesFromBoard(id);
        return ResponseEntity.ok(messageOutputDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageBoardOutputDto> updateMessageBoardInfo(@PathVariable Long id, @Valid @RequestBody MessageBoardInputDto upMessageBoard, BindingResult br) {
        if (br.hasErrors()) {
            String errorMessage = "Fout bij het verwerken van de request";
            throw new BadRequestException(errorMessage);
        }
        MessageBoardOutputDto messageBoardOutputDto = messageBoardService.updateMessageBoardInfo(id, upMessageBoard);
        return ResponseEntity.ok().body(messageBoardOutputDto);
    }
}


