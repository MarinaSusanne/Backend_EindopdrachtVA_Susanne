package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.*;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.MaxActiveGroupsException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.*;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public List<UserLeanOutputDto> getUsersByGroupId(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No group found"));
        List<User> users = group.getUsers();
        if (users.isEmpty()) {
            throw new RecordNotFoundException("No users found");
        }
        {
            List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
            for (User u : users) {
                UserLeanOutputDto udto = UserMapper.userToUserLeanDto(u);
                userLeanOutputDtos.add(udto);
            }
            //To add in servide layer: addning the admins so they are also visible in the groups
            List<User> admins = userRepository.findUsersByAdminAuthority();
            for (User admin : admins) {
                UserLeanOutputDto admindto = UserMapper.userToUserLeanDto(admin);
                userLeanOutputDtos.add(admindto);
            }
            return userLeanOutputDtos;
        }
    }

    @Override
    @Transactional
    public GroupWithPicturesOutputDto getMyGroup(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No user found"));
        Group group = user.getGroup();
        if (group == null) {
            throw new RecordNotFoundException("User is not part of a group");
        }
        return createGroupPictureOutputDto(group);
    }

    @Override
    @Transactional
    public GroupWithPicturesOutputDto getGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No group found"));
        return createGroupPictureOutputDto(group);
    }

    @Override
    @Transactional
    public List<GroupOutputDto> getMyActiveGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupOutputDto> activeGroups = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (Group g : groups) {
            LocalDate startDate = g.getStartDate();
            LocalDate endDate = g.getEndDate();
            if (startDate != null && endDate != null &&
                    currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                GroupOutputDto groupOutputDto = createGroupOutputDto(g);
                activeGroups.add(groupOutputDto);
            }
        }
        if (activeGroups.isEmpty()) {
            throw new RecordNotFoundException("No active groups found");
        }
        return activeGroups;
    }

    @Override
    @Transactional
    public GroupWithPicturesOutputDto getSpecificGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Group found with this ID"));
        return createGroupPictureOutputDto(group);
    }

    private GroupOutputDto createGroupOutputDto(Group group) {
        GroupOutputDto groupOutputDto = modelMapper.map(group, GroupOutputDto.class);
        groupOutputDto.setMessageBoardId(group.getMessageBoard().getId());
        List<User> userList = group.getUsers();
        List<UserLeanOutputDto> userleanOutputDtos = new ArrayList<>();
        for (User u : userList) {
            userleanOutputDtos.add(UserMapper.userToUserLeanDto(u));
        }
        groupOutputDto.setUserLeanOutputDtos(userleanOutputDtos);
        return groupOutputDto;
    }

    private GroupWithPicturesOutputDto createGroupPictureOutputDto(Group group) {
        GroupWithPicturesOutputDto groupPictureOutputDto = modelMapper.map(group, GroupWithPicturesOutputDto.class);
        groupPictureOutputDto.setMessageBoardId(group.getMessageBoard().getId());
        List<User> userList = group.getUsers();
        List<UserPictureOutputDto> userPictureOutputDtos = new ArrayList<>();
        for (User u : userList) {
            userPictureOutputDtos.add(UserMapper.userToUserPictureDto(u));
        }
        //To add in service layer: adding the admins so they are also visible in the groups
        List<User> admins = userRepository.findUsersByAdminAuthority();
        for (User admin : admins) {
            UserPictureOutputDto admindto = UserMapper.userToUserPictureDto(admin);
            userPictureOutputDtos.add(admindto);
        }
        groupPictureOutputDto.setUserPictureOutputDtos(userPictureOutputDtos);
        return groupPictureOutputDto;
    }

    @Override
    @Transactional
    public GroupOutputDto createGroup(GroupInputDto groupInputDto) {
        validateGroupDates(groupInputDto.getStartDate(), groupInputDto.getEndDate());
        List<Group> activeGroups = checkAmountActiveGroups();
        if (activeGroups.size() >= 3) {
            throw new MaxActiveGroupsException("There are already three active groups");
        }
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
        //A messageboard is automatically created when a group is created
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setGroup(group);
        messageBoardRepository.save(messageBoard);
        group.setMessageBoard(messageBoard);
        groupRepository.save(group);
        GroupOutputDto groupOutputDto = modelMapper.map(group, GroupOutputDto.class);
        groupOutputDto.setMessageBoardId(group.getMessageBoard().getId());

        groupOutputDto.setUserLeanOutputDtos(userLeanOutputDtos);
        return groupOutputDto;
    }

    @Override
    @Transactional
    public List<Group> checkAmountActiveGroups() {
        List<Group> groups = groupRepository.findAll();
        List<Group> activeGroups = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (Group g : groups) {
            LocalDate startDate = g.getStartDate();
            LocalDate endDate = g.getEndDate();
            if (startDate != null && endDate != null &&
                    currentDate.isAfter(startDate) && currentDate.isBefore(endDate)) {
                activeGroups.add(g);
            }
        }
        return activeGroups;
    }

    private void validateGroupDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }

    }
}
