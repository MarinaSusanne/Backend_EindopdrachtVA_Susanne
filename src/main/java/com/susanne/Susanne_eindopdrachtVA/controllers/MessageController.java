package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<List<MessageOutputDto>> getAllMessages() {
        List<MessageOutputDto> messageOutput = messageService.getAllMessages();
        return ResponseEntity.ok(messageOutput);
    }
    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> createAndAssignMessage(@Valid @PathVariable Long userId, @RequestBody MessageInputDto messageInputDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        User user = optionalUser.get();
        MessageOutputDto messageOutputDto = messageService.createAndAssignMessage(user, messageInputDto);
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
