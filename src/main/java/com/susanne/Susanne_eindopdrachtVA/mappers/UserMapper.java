package com.susanne.Susanne_eindopdrachtVA.mappers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserPutInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UserMapper {

    public UserOutputDto userToUserDto(User user) {
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


    public User userDtoToUser(UserInputDto inputDto) {
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

    public User updateUser (User user, UserPutInputDto upUser) {

        if (!ObjectUtils.isEmpty(upUser.getUsername())) {
            user.setUsername(upUser.getUsername());
        }

        if (!ObjectUtils.isEmpty(upUser.getEmail())) {
            user.setEmail(upUser.getEmail());
        }

        if (!ObjectUtils.isEmpty(upUser.getFirstName())) {
            user.setFirstName(upUser.getFirstName());
        }

        if (!ObjectUtils.isEmpty(upUser.getLastName())) {
            user.setLastName(upUser.getLastName());
        }

        if (!ObjectUtils.isEmpty(upUser.getStreetName())) {
            user.setStreetName(upUser.getStreetName());
        }

        if (!ObjectUtils.isEmpty(upUser.getHouseNumber())) {
            user.setHouseNumber(upUser.getHouseNumber());
        }

        if (!ObjectUtils.isEmpty(upUser.getZipcode())) {
            user.setZipcode(upUser.getZipcode());
        }

        if (!ObjectUtils.isEmpty(upUser.getCity())) {
            user.setCity(upUser.getCity());
        }

        if (!ObjectUtils.isEmpty(upUser.getPhoto())) {
            user.setPhoto(upUser.getPhoto());
        }
        return user;

    }
}

