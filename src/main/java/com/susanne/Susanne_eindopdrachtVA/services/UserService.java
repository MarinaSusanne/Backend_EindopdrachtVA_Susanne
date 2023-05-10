package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserPutInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.NoUsersWithoutGroupException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.hibernate.validator.internal.util.logging.Messages;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, MessageMapper messageMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
    }

    public List<UserOutputDto> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserOutputDto> userOutputDtos = new ArrayList<>();
        for (User u : users) {
            UserOutputDto udto = userMapper.userToUserDto(u);
            userOutputDtos.add(udto);
        }
        return userOutputDtos;
    }

    public UserOutputDto getOneUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return userMapper.userToUserDto(user);
    }

    public List<MessageOutputDto> getUserMessagesByUserId(Long Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new RecordNotFoundException("User not found"));
        List<Message> messages = user.getMessages();
        if (messages == null) {
            throw new RecordNotFoundException("No messages found");
        } else {
            List<MessageOutputDto> messageOutputDtos = new ArrayList<>();
            for (Message m : messages) {
                MessageOutputDto mdto = messageMapper.messageToMessageDto(m);
                messageOutputDtos.add(mdto);
            }
            return messageOutputDtos;
        }
    }

    public List<UserOutputDto> getUsersWithoutGroup() {
        Iterable<User> users = userRepository.findAll();
        List<UserOutputDto> userOutputDtos = new ArrayList<>();
        for (User u : users) {
            if (u.getGroup() == null) {
                UserOutputDto udto = userMapper.userToUserDto(u);
                userOutputDtos.add(udto);
            }

        }
        if (userOutputDtos.isEmpty()) {
            throw new NoUsersWithoutGroupException("Er zijn geen gebruikers zonder groep gevonden.");
        }
        return userOutputDtos;
    }

    public UserOutputDto createUser(UserInputDto inputDto) {
        User user = userMapper.userDtoToUser(inputDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public void deleteUser(@RequestBody Long id) {
        userRepository.deleteById(id);
    }

    public UserOutputDto updateUser(Long id, UserPutInputDto upUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userMapper.updateUser(userOptional.get(), upUser);
            User updatedUser = userRepository.save(user);
            return userMapper.userToUserDto(updatedUser);

        } else {
            throw new RecordNotFoundException("No User found!");
        }
    }
    }
    //TODO: nog andere methoden toevoegen
    //TODO:exception handling


 
        

