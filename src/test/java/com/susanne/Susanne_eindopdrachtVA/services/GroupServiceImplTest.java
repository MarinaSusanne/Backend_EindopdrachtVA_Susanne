package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.UserMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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
    ArgumentCaptor<Message> captor;
    List<User> usersList1;
    List<User> usersList2;
    User user1;
    User user2;
    User user3;
    User user4;
    Group group1;
     Group group2;
    Group group3;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("TestUsernaam1");
        user1.setEmail("email1@example.com");
        user1.setPassword("wachtwoord123");
        user1.setFirstName("Hendrike");
        user1.setLastName("Jansen");
        user1.setStreetName("Bosweg");
        user1.setHouseNumber("5B");
        user1.setZipcode("3579EK");
        user1.setCity("Utrecht");
        user1.setDateOfBirth(LocalDate.of(1999, 6, 8));


        user2 = new User();
        user2.setId(2L);
        user2.setUsername("TestUsernaam2");
        user2.setEmail("email2@example.com");
        user2.setPassword("wachtwoord456");
        user2.setFirstName("Kees");
        user2.setLastName("Pietersen");
        user2.setStreetName("Straatweg");
        user2.setHouseNumber("10");
        user2.setZipcode("1234AB");
        user2.setCity("Amsterdam");
        user2.setDateOfBirth(LocalDate.of(2000, 01, 01));

        user3 = new User();
        user3.setId(3L);
        user3.setUsername("TestUsernaam3");
        user3.setEmail("email3@example.com");
        user3.setPassword("wachtwoord789");
        user3.setFirstName("Liesbeth");
        user3.setLastName("van Dijk");
        user3.setStreetName("Pleinlaan");
        user3.setHouseNumber("3");
        user3.setZipcode("5678CD");
        user3.setCity("Den Haag");
        user3.setDateOfBirth(LocalDate.of(1995, 11, 30));

        user4 = new User();
        user4.setId(4L);
        user4.setUsername("JanJansen");
        user4.setEmail("janjansen@example.com");
        user4.setPassword("wachtwoord123");
        user4.setFirstName("Jan");
        user4.setLastName("Jansen");
        user4.setStreetName("Voorbeeldstraat");
        user4.setHouseNumber("10");
        user4.setZipcode("1234 AB");
        user4.setCity("Amsterdam");
        user4.setDateOfBirth(LocalDate.of(1985, 9, 20));

        usersList1 = Arrays.asList(user1, user3);
        usersList2 = Arrays.asList(user2);

        group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Naam van Groep");
        group1.setStartDate(LocalDate.of(2023, 5, 29));
        group1.setEndDate(LocalDate.of(2023, 12, 22));
        group1.setGroupInfo("groepinfo die vet leuk is");
        group1.setUsers(usersList1);

        group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Naam van Groep 2");
        group2.setStartDate(LocalDate.of(2023, 5, 29));
        group2.setEndDate(LocalDate.of(2023, 12, 29));
        group2.setGroupInfo("groepinfo die nog veel leuker is");
        group2.setUsers(usersList2);

        group3 = new Group();
        group3.setId(3L);
        group3.setGroupName("Naam van Groep 3");
        group3.setStartDate(LocalDate.of(2023, 7, 29));
        group3.setEndDate(LocalDate.of(2023, 11, 21));
        group3.setGroupInfo("userloze groep");
        group3.setUsers((Collections.emptyList()));


        //Hier leg ik in de relaties die ik niet hierboven al kan maken
        user1.setGroup(group1);
        user2.setGroup(group2);
        user3.setGroup(group1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getUsersByGroupId() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group1));
        List<User> testUsers = group1.getUsers();
        UserLeanOutputDto testLean1 = UserMapper.userToUserLeanDto(testUsers.get(0));
        UserLeanOutputDto testLean2 = UserMapper.userToUserLeanDto(testUsers.get(1));

        // Assert
        List<UserLeanOutputDto> result = groupServiceImpl.getUsersByGroupId(group1.getId());

        assertEquals(testUsers.size(), result.size());
        assertEquals(testLean1.getId(), result.get(0).getId());
        assertEquals(testLean1.getFirstName(), result.get(0).getFirstName());
        assertEquals(testLean2.getId(), result.get(1).getId());
        assertEquals(testLean2.getLastName(), result.get(1).getLastName());
        }

    @Test
//    @Disabled
    void testGetUsersByGroupId_NoGroupFound() {
        // Arrange
        when(groupRepository.findById(101L)).thenReturn(Optional.empty());
        //act & assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> groupServiceImpl.getUsersByGroupId(101L));
        verify(groupRepository, times(1)).findById(101L);
        assertEquals("No group found", exception.getMessage());
    }

    @Test
    void testGetUsersByGroupId_NoUsersFound() {
        // Arrange
        when(groupRepository.findById(3L)).thenReturn(Optional.of(group3));

        // Act & Assert & verify
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> groupServiceImpl.getUsersByGroupId(3L));
        verify(groupRepository, times(1)).findById(3L);
        assertEquals("No users found", exception.getMessage());
    }



    @Test
    @Disabled
    void getMyGroup() {
        //arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Group testGroup = user1.getGroup();

        // Act
        GroupOutputDto result = groupServiceImpl.getMyGroup(1L);
        when(modelMapper.map(testGroup, GroupOutputDto.class)).thenReturn(new GroupOutputDto());

        // Assert
        assertEquals(testGroup.getId(), result.getId());
        assertEquals(testGroup.getGroupName(), result.getGroupName());
        verify(userRepository).findById(1L);
        verify(modelMapper).map(testGroup, GroupOutputDto.class);
    }

    @Test
    @Disabled
    void testGetMyGroup_UserNotInGroup() {
        // Arrange
        when(userRepository.findById(user4.getId())).thenReturn(Optional.of(user4));

        // Act & Assert & verify
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            groupServiceImpl.getMyGroup(user4.getId());
        });
        verify(userRepository, times(1)).findById(user4.getId());
        assertEquals("User is not part of a group", exception.getMessage());
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