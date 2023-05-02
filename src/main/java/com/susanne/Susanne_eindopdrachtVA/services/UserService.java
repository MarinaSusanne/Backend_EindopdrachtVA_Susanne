package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserPutInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    public List<Message> getUserMessages(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return user.getMessages();
    }

    public List<Message> getUserMessagesByName(String username) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new RecordNotFoundException("User not found");
        }
        return user.getMessages();
    }

    //TODO: aanpassen exception handling!

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


 
        

