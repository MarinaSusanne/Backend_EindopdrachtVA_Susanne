package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.model.User;

import java.util.List;

public interface GroupService {
        List<User> getUsersByGroupName(String groupName);
}
