package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GroupServiceImplTest {

    @Mock
    GroupRepository groupRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    MessageBoardRepository messageBoardRepository;

    @InjectMocks
    GroupServiceImpl groupServiceImpl;

    @Captor
    ArgumentCaptor<Group> argumentCaptor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getUsersByGroupId() {
    }

    @Test
    @Disabled
    void getMyGroup() {
    }

    @Test
    @Disabled
    void getMyActiveGroups() {
    }

    @Test
    @Disabled
    void getSpecificGroup() {
    }

    @Test
    @Disabled
    void createGroup() {
    }
}