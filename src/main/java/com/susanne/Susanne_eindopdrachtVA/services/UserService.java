package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

        private final UserRepository userRepository;
        
        public UserService(UserRepository userRepository){
            this.userRepository = userRepository;
        }

    public List<UserOutputDto> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        List <UserOutputDto> userOutputDtos = new ArrayList<>();
        for (User u : users) {
            UserOutputDto udto = transferEntityToDto(u);
            userOutputDtos.add(udto);
        }
        return userOutputDtos;
    }

    public UserOutputDto getOneUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User not found"));
        return transferEntityToDto(user);
    }

    //TODO: eventueel nog een exceptions gooien als er geen Users zijn?

    public UserOutputDto createUser(UserInputDto inputDto){
        User user = transferDtoToEntity(inputDto);
        userRepository.save(user);
        return transferEntityToDto(user);
    }

    public void deleteUser(@RequestBody Long id) {
        userRepository.deleteById(id);
    }

    public UserOutputDto updateUser(Long id, UserInputDto upUser){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(upUser.getUsername());
            user.setEmail(upUser.getEmail());
            user.setFirstName(upUser.getFirstName());
            user.setLastName(upUser.getLastName());
            user.setStreetName(upUser.getStreetName());
            user.setHouseNumber(upUser.getHouseNumber());
            user.setZipcode(upUser.getZipcode());
            user.setCity(upUser.getCity());
            user.setDateOfBirth(upUser.getDateOfBirth());
            user.setPhoto(upUser.getPhoto());
            User updatedUser = userRepository.save(user);
            return transferEntityToDto(updatedUser);

        }
        else{
            throw new RecordNotFoundException("No User found!");
        }
    }

    public User transferDtoToEntity (UserInputDto inputDto){
        User user = new User();
        user.setUsername(inputDto.getUsername());
        user.setPassword(inputDto.getPassword());
        user.setEmail(inputDto.getEmail());
        user.setFirstName(inputDto.getFirstName());
        user.setLastName(inputDto.getLastName());
        user.setStreetName(inputDto.getStreetName());
        user.setHouseNumber(inputDto.getHouseNumber());
        user.setZipcode(inputDto.getZipcode());
        user.setCity(inputDto.getCity());
        user.setDateOfBirth(inputDto.getDateOfBirth());
        user.setPhoto(inputDto.getPhoto());
        return user;
    }

    public UserOutputDto transferEntityToDto (User user){
        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setId(user.getId());
        outputDto.setUsername(user.getUsername());
        outputDto.setEmail(user.getEmail());
        outputDto.setFirstName(user.getFirstName());
        outputDto.setLastName(user.getLastName());
        outputDto.setStreetName(user.getStreetName());
        outputDto.setHouseNumber(user.getHouseNumber());
        outputDto.setZipcode(user.getZipcode());
        outputDto.setCity(user.getCity());
        outputDto.setDateOfBirth(user.getDateOfBirth());
        outputDto.setPhoto(user.getPhoto());
        return outputDto;
    }
}
 
        

