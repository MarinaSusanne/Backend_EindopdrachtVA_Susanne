package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


//@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public ResponseEntity<List<MessageOutputDto>> getAllMessages() {
        List<MessageOutputDto> messageOutput = messageService.getAllMessages();
        return ResponseEntity.ok(messageOutput);
    }
    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> createAndAssignMessage(@Valid @PathVariable Long userId, @RequestBody MessageInputDto messageInputDto) {
        MessageOutputDto messageOutputDto = messageService.createAndAssignMessage(userId, messageInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + messageOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(messageOutputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageOutputDto> updateMessage(@PathVariable Long id, @Valid @RequestBody MessageInputDto upMessage, BindingResult br) {
        if (br.hasErrors()) {
            String errorMessage = "Fout bij het verwerken van de request";
            throw new BadRequestException(errorMessage);
        }
        MessageOutputDto messageOutputDto = messageService.updateMessage(id, upMessage);
        return ResponseEntity.ok().body(messageOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }


}
