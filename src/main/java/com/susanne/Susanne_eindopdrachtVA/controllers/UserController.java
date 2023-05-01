package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserPutInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> userOutput = userService.getAllUsers();
        return ResponseEntity.ok(userOutput);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<UserOutputDto> getOneUserById(@PathVariable Long id) {
        UserOutputDto userOutputDto = userService.getOneUserById(id);
        return ResponseEntity.ok(userOutputDto);
    }

    //TODO: toevoegen getMapping op basis van naam

    @PostMapping()
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto UserInputDto) {
        UserOutputDto userOutputDto = userService.createUser(UserInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + userOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(userOutputDto);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserPutInputDto upUser) {
        UserOutputDto userOutputDto = userService.updateUser(id, upUser);
        return ResponseEntity.ok().body(userOutputDto);

    }
}

