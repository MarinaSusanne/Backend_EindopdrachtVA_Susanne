package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("find/{id}")
    public ResponseEntity<MessageOutputDto> getOneMessageById(@PathVariable Long id) {
        MessageOutputDto messageOutputDto = messageService.getOneMessageById(id);
        return ResponseEntity.ok(messageOutputDto);
    }

    //TODO: toevoegen getMapping op basis van auteur

    @PostMapping()
    public ResponseEntity<MessageOutputDto> createMessage(@Valid @RequestBody MessageInputDto messageInputDto) {
        MessageOutputDto messageOutputDto = messageService.createMessage(messageInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + messageOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(messageOutputDto);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageOutputDto> updateMessage(@PathVariable Long id, @Valid @RequestBody MessageInputDto updateMessage) {
        MessageOutputDto messageOutputDto = messageService.updateMessage(id, updateMessage);
        return ResponseEntity.ok().body(messageOutputDto);

    }
}