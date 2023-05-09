package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
  @Override
  public List<UserLeanOutputDto> getUsersByGroupName(String groupName) {
       return
    }







//TODO: bij createGroup ook gelijk een messageBoard aanmaken!