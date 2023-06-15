package com.susanne.Susanne_eindopdrachtVA.services;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserPutInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.NoUsersWithoutGroupException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.UsernameNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Authority;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.utils.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, MessageMapper messageMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserOutputDto> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserOutputDto> userOutputDtos = new ArrayList<>();
        for (User u : users) {
            UserOutputDto udto = UserMapper.userToUserDto(u);
            userOutputDtos.add(udto);
        }
        return userOutputDtos;
    }

    public UserOutputDto getOneUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return UserMapper.userToUserDto(user);
    }

    public String getUsername(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return user.getUsername();
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

    public List<UserLeanOutputDto> getUsersWithoutGroup() {
        Iterable<User> users = userRepository.findAll();
        List<UserLeanOutputDto> userOutputDtos = new ArrayList<>();
        for (User u : users) {
            if (u.getGroup() == null) {
                UserLeanOutputDto udto = userMapper.userToUserLeanDto(u);
                userOutputDtos.add(udto);
            }
        }
        if (userOutputDtos.isEmpty()) {
            throw new NoUsersWithoutGroupException("Er zijn geen gebruikers zonder groep gevonden.");
        }
        return userOutputDtos;
    }

    public UserOutputDto createUser(UserInputDto inputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        inputDto.setApikey(randomString);
        inputDto.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        User user = UserMapper.userDtoToUser(inputDto);
        userRepository.save(user); // SAve user to receive ID;
        String authority = "ROLE_USER";
        user.addAuthority(new Authority(user.getId(), authority));
        userRepository.save(user);
        return UserMapper.userToUserDto(user);
    }


    public void deleteUser(@RequestBody Long id) {
        userRepository.deleteById(id);
    }

    public UserOutputDto updateUser(Long id, UserPutInputDto upUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = UserMapper.updateUser(userOptional.get(), upUser);
            User updatedUser = userRepository.save(user);
            return UserMapper.userToUserDto(updatedUser);

        } else {
            throw new RecordNotFoundException("No User found!");
        }
    }


    public Set<Authority> getAuthorities(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User bestaat niet"));
        UserOutputDto userDto = userMapper.userToUserDto(user);
        return userDto.getAuthorities();
    }


    public void addAuthority(String username, String authority) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.addAuthority(new Authority(user.getId(), authority));
        userRepository.save(user);
    }

    public void removeAuthority(Long id, String authority) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User bestaat niet"));
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("username not ffound");
        } else {
            return optionalUser.get();
        }
    }
}



 
        

