package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public List<UserLeanOutputDto> getUsersByGroupId(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No group found"));
        List<User> users = group.getUsers();
        if (users.isEmpty()) {
            throw new RecordNotFoundException("No users found");
        } else {
            List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
            for (User u : users) {
                UserLeanOutputDto udto = UserMapper.userToUserLeanDto(u);
                userLeanOutputDtos.add(udto);
            }
            return userLeanOutputDtos;
        }
    }

    @Override
    public GroupOutputDto getMyGroup(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No user found"));
    //HIER WAS IK!!!!!

        return null;
    }

    @Override
    public List<GroupOutputDto> getMyActiveGroups() {
        return null;
    }

    @Override
    public GroupOutputDto getSpecificGroup(Long id) {
        return null;
    }

    @Override
    public GroupOutputDto createGroup(GroupInputDto groupInputDto) {
        //TODO: bij createGroup ook gelijk een messageBoard aanmaken!
        return null;
    }

    @Override
    public GroupOutputDto updateGroup(Long id, GroupInputDto upGroup) {
        return null;
    }
}






