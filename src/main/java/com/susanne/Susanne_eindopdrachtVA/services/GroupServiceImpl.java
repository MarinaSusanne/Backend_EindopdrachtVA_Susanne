package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.*;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Import(AppConfig.class)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final MessageBoardRepository messageBoardRepository;


    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserRepository userRepository, MessageBoardRepository messageBoardRepository) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.messageBoardRepository = messageBoardRepository;
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No user found"));
        Group group = user.getGroup();
        if (group == null) {
            throw new RecordNotFoundException("User is not part of a group");
        }
        GroupOutputDto groupOutputDto = modelMapper.map(group, GroupOutputDto.class);
//        List<User> userList = group.getUsers();
//        List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
//        for (User u : userList) {
//            userLeanOutputDtos.add(UserMapper.userToUserLeanDto(u));
//        }
//        groupOutputDto.setUserLeanOutputDto(userLeanOutputDtos);
        return groupOutputDto;
    }

    @Override
    public List<GroupOutputDto> getMyActiveGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupOutputDto> activeGroups = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Group g : groups) {
            LocalDate startDate = g.getStartDate();
            LocalDate endDate = g.getEndDate();
            if (startDate != null && endDate != null &&
                    currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                GroupOutputDto groupOutputDto = modelMapper.map(g, GroupOutputDto.class);
//                System.out.println(groupOutputDto);
//                List<User> userList = g.getUsers();
//                List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
//                for (User u : userList) {
//                     userLeanOutputDtos.add(UserMapper.userToUserLeanDto(u));
//                    }
//                groupOutputDto.setUserLeanOutputDto(userLeanOutputDtos);
                activeGroups.add(groupOutputDto);
                }
            }
            if (activeGroups.isEmpty()) {
                throw new RecordNotFoundException("No active groups found");
            }
            return activeGroups;
        }

    @Override
    public GroupOutputDto getSpecificGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Group found with this ID"));
        GroupOutputDto groupOutputDto = modelMapper.map(group, GroupOutputDto.class);
        return groupOutputDto;
    }


    @Override
    public GroupOutputDto createGroup(GroupInputDto groupInputDto) {
        validateGroupDates(groupInputDto.getStartDate(), groupInputDto.getEndDate());

        List<Long> userIds = groupInputDto.getUsers();
        List<User> userList = new ArrayList<>();
        List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
        Group group = modelMapper.map(groupInputDto, Group.class);
        for (Long u : userIds) {
            Optional<User> userOptional = userRepository.findById(u);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userList.add(user);
                user.setGroup(group);
                userLeanOutputDtos.add(UserMapper.userToUserLeanDto(user));
            }
        }
        group.setUsers(userList);
        groupRepository.save(group);
        //Er wordt gelijk een messageboard aangemaakt zodra een groep wordt aangemaakt
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setGroup(group);
        messageBoardRepository.save(messageBoard);
        group.setMessageBoard(messageBoard);
        groupRepository.save(group);
        GroupOutputDto groupOutputDto = modelMapper.map(group, GroupOutputDto.class);
        groupOutputDto.setUserLeanOutputDto(userLeanOutputDtos);
        return groupOutputDto;
    }

    private void validateGroupDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

    }
}








