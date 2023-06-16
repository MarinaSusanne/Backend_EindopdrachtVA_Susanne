package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupWithPicturesOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GroupServiceImplTest {

    @Mock
    GroupRepository groupRepository;
    @Mock
    MessageBoardRepository messageBoardRepository;
    @Mock
    ModelMapper modelMapper;
    @Mock
    UserRepository userRepository;

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
    User user5;
    User user6;
    Group group1;
     Group group2;
    Group group3;
    Group group4;

    Group group5;

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

        user5 = new User();
        user5.setId(5L);
        user5.setUsername("TestGebruiker5");
        user5.setEmail("gebruiker5@example.com");
        user5.setPassword("wachtwoord456");
        user5.setFirstName("Sophie");
        user5.setLastName("Bakker");
        user5.setStreetName("Dorpsstraat");
        user5.setHouseNumber("25");
        user5.setZipcode("1234 AB");
        user5.setCity("Utrecht");
        user5.setDateOfBirth(LocalDate.of(1990, 5, 15));

        user6 = new User();
        user6.setId(6L);
        user6.setUsername("EvaSmit");
        user6.setEmail("evasmit@example.com");
        user6.setPassword("wachtwoord789");
        user6.setFirstName("Eva");
        user6.setLastName("Smit");
        user6.setStreetName("Hoge Laan");
        user6.setHouseNumber("7");
        user6.setZipcode("5678 CD");
        user6.setCity("Rotterdam");
        user6.setDateOfBirth(LocalDate.of(1988, 8, 10));

        usersList1 = Arrays.asList(user1, user3);
        usersList2 = Arrays.asList(user2);

        group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Naam van Groep");
        group1.setStartDate(LocalDate.of(2023, 5, 12));
        group1.setEndDate(LocalDate.of(2023, 12, 22));
        group1.setGroupInfo("groepinfo die vet leuk is");
        group1.setUsers(usersList1);

        group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Naam van Groep 2");
        group2.setStartDate(LocalDate.of(2023, 5, 13));
        group2.setEndDate(LocalDate.of(2023, 12, 29));
        group2.setGroupInfo("groepinfo die nog veel leuker is");
        group2.setUsers(usersList2);

        group3 = new Group();
        group3.setId(3L);
        group3.setGroupName("Naam van Groep 3");
        group3.setStartDate(LocalDate.of(2023, 5, 14));
        group3.setEndDate(LocalDate.of(2023, 11, 21));
        group3.setGroupInfo("userloze groep");
        group3.setUsers((new ArrayList<>()));

        group4 = new Group();
        group4.setId(4L);
        group4.setGroupName("Naam van Groep 4");
        group4.setStartDate(LocalDate.of(2022, 7, 29));
        group4.setEndDate(LocalDate.of(2023, 4, 21));
        group4.setGroupInfo("Groep die niet meer actief is");
        group4.setUsers((new ArrayList<>()));

        group5 = new Group();
        group5.setId(5L);
        group5.setGroupName("Naam van Groep 5");
        group5.setStartDate(LocalDate.of(2022, 8, 29));
        group5.setEndDate(LocalDate.of(2023, 5, 21));
        group5.setGroupInfo("Nog een groep die niet meer actief is");
        group5.setUsers((new ArrayList<>()));


        //Hier leg ik in de relaties die ik niet hierboven al kan maken
        user1.setGroup(group1);
        user2.setGroup(group2);
        user3.setGroup(group1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
//    @Disabled
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
//    @Disabled
    void testGetUsersByGroupId_NoUsersFound() {
        // Arrange
        when(groupRepository.findById(3L)).thenReturn(Optional.of(group3));

        // Act & Assert & verify
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> groupServiceImpl.getUsersByGroupId(3L));
        verify(groupRepository, times(1)).findById(3L);
        assertEquals("No users found", exception.getMessage());
    }



    @Test
//    @Disabled
    void getMyGroup() {
        //arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Group testGroup = user1.getGroup();
        GroupWithPicturesOutputDto groupOutputDto = new GroupWithPicturesOutputDto();
        groupOutputDto.setGroupInfo(group1.getGroupInfo());
        groupOutputDto.setGroupName(group1.getGroupName());
        groupOutputDto.setId(group1.getId());
        groupOutputDto.setStartDate(group1.getStartDate());
        groupOutputDto.setUserPictureOutputDtos(new ArrayList<>());
        when(modelMapper.map((Object) any(), (Class<GroupWithPicturesOutputDto>) any())).thenReturn(groupOutputDto);

        // Act
        GroupWithPicturesOutputDto result = groupServiceImpl.getMyGroup(1L);

        // Assert
        assertEquals(testGroup.getId(), result.getId());
        assertEquals(testGroup.getGroupName(), result.getGroupName());
        assertEquals(testGroup.getGroupInfo(), result.getGroupInfo());
        verify(userRepository).findById(1L);
        verify(modelMapper).map(testGroup, GroupOutputDto.class);
    }

    @Test
//    @Disabled
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
//    @Disabled
    void getMyActiveGroups() {
            //arrange
            List<Group> groups = List.of(group1, group2, group3, group4);
            when(groupRepository.findAll()).thenReturn(groups);
            LocalDate currentDate = LocalDate.now();
            List<GroupOutputDto> expectedActiveGroups = new ArrayList<>();

            GroupOutputDto groupOutputDto1 = new GroupOutputDto();
            groupOutputDto1.setGroupInfo(group1.getGroupInfo());
            groupOutputDto1.setGroupName(group1.getGroupName());
            groupOutputDto1.setId(group1.getId());
            groupOutputDto1.setStartDate(group1.getStartDate());
            groupOutputDto1.setUserLeanOutputDtos(new ArrayList<>());
            when(modelMapper.map(group1, GroupOutputDto.class)).thenReturn(groupOutputDto1);

            GroupOutputDto groupOutputDto2 = new GroupOutputDto();
            groupOutputDto2.setGroupInfo(group2.getGroupInfo());
            groupOutputDto2.setGroupName(group2.getGroupName());
            groupOutputDto2.setId(group2.getId());
            groupOutputDto2.setStartDate(group2.getStartDate());
            groupOutputDto2.setUserLeanOutputDtos(new ArrayList<>());
            when(modelMapper.map(group2, GroupOutputDto.class)).thenReturn(groupOutputDto2);

            GroupOutputDto groupOutputDto3 = new GroupOutputDto();
            groupOutputDto3.setGroupInfo(group3.getGroupInfo());
            groupOutputDto3.setGroupName(group3.getGroupName());
            groupOutputDto3.setId(group3.getId());
            groupOutputDto3.setStartDate(group3.getStartDate());
            groupOutputDto3.setUserLeanOutputDtos(new ArrayList<>());
            when(modelMapper.map(group3, GroupOutputDto.class)).thenReturn(groupOutputDto3);

            expectedActiveGroups.add(groupOutputDto1);
            expectedActiveGroups.add(groupOutputDto2);
            expectedActiveGroups.add(groupOutputDto3);

            //act
            List<GroupOutputDto> result = groupServiceImpl.getMyActiveGroups();

            //assert
            assertEquals(expectedActiveGroups, result);
            verify(groupRepository, times(1)).findAll();
            assertEquals(expectedActiveGroups.get(1).getGroupInfo(), result.get(1).getGroupInfo());
            assertEquals(expectedActiveGroups.get(2).getGroupName(), result.get(2).getGroupName());
            assertEquals(expectedActiveGroups.get(0).getGroupName(), result.get(0).getGroupName());
            assertEquals(expectedActiveGroups.size(), result.size());
        }


    @Test
//    @Disabled
    void getMyActiveGroups_NoActiveGroups() {
        //arrange
        List<Group> groups = List.of(group4, group5);
        when(groupRepository.findAll()).thenReturn(groups);
        LocalDate currentDate = LocalDate.now();
        List<GroupOutputDto> expectedActiveGroups = new ArrayList<>();

        //act and assert
        RecordNotFoundException exception =  assertThrows(RecordNotFoundException.class, () -> {
            groupServiceImpl.getMyActiveGroups();
        });

        verify(groupRepository, times(1)).findAll();
        assertEquals("No active groups found", exception.getMessage());
        verifyNoMoreInteractions(groupRepository);
    }


    @Test
//    @Disabled
        void getSpecificGroup() {
            //arrange
            when(groupRepository.findById(group1.getId())).thenReturn(Optional.of(group1));
            GroupOutputDto groupOutputDto = new GroupOutputDto();
            groupOutputDto.setGroupInfo(group1.getGroupInfo());
            groupOutputDto.setGroupName(group1.getGroupName());
            groupOutputDto.setId(group1.getId());
            groupOutputDto.setStartDate(group1.getStartDate());

            List<UserLeanOutputDto> userLeanOutputDtos = new ArrayList<>();
            userLeanOutputDtos.add(UserMapper.userToUserLeanDto(user1));
            userLeanOutputDtos.add(UserMapper.userToUserLeanDto(user3));
            //user4 is niet onderdeel van groep1, gebruik dit om assertFalse te testen
            userLeanOutputDtos.add(UserMapper.userToUserLeanDto(user4));
            groupOutputDto.setUserLeanOutputDtos(userLeanOutputDtos);

            when(modelMapper.map(group1, GroupOutputDto.class)).thenReturn(groupOutputDto);

            // Act
            GroupWithPicturesOutputDto result = groupServiceImpl.getSpecificGroup(group1.getId());

            // Assert
            assertEquals(groupOutputDto.getId(), result.getId());
            assertEquals(groupOutputDto.getGroupName(), result.getGroupName());
            assertEquals(groupOutputDto.getGroupInfo(), result.getGroupInfo());
            assertEquals(groupOutputDto.getUserLeanOutputDtos().get(0).getLastName(), result.getUserPictureOutputDtos().get(0).getLastName());
            verify(modelMapper).map(group1, GroupOutputDto.class);
            assertFalse(groupOutputDto.getUserLeanOutputDtos().contains(UserMapper.userToUserLeanDto(user4)));
    }

    @Test
//    @Disabled
    void getSpecificGroup_NoGroupFound() {
        //arrange
        when(groupRepository.findById(any())).thenReturn(Optional.empty());

        //act and assert
        RecordNotFoundException exception =  assertThrows(RecordNotFoundException.class, ()
                -> groupServiceImpl.getSpecificGroup(101L));
        verify(groupRepository, times(1)).findById(101L);
        assertEquals("No Group found with this ID", exception.getMessage());
    }

    @Test
//    @Disabled
      void createGroup() {
        //arrange
        GroupInputDto groupInputDto = new GroupInputDto();
        groupInputDto.setStartDate(LocalDate.of(2023, 5, 23));
        groupInputDto.setEndDate(LocalDate.of(2024, 6, 27));
        groupInputDto.setGroupName("Super coole naam");
        groupInputDto.setUsers(Arrays.asList(5L, 6L));

        Group group = new Group();
        when(modelMapper.map(groupInputDto, Group.class)).thenReturn(group);
        when(modelMapper.map(group, GroupOutputDto.class)).thenReturn(new GroupOutputDto());
        when(userRepository.findById(5L)).thenReturn(Optional.of(user5));
        when(userRepository.findById(6L)).thenReturn(Optional.of(user6));

        ArgumentCaptor<Group> groupCaptor = ArgumentCaptor.forClass(Group.class);
        ArgumentCaptor<MessageBoard> messageBoardCaptor = ArgumentCaptor.forClass(MessageBoard.class);

        // Act
        GroupOutputDto result = groupServiceImpl.createGroup(groupInputDto);

        // Assert
        verify(groupRepository, times(2)).save(groupCaptor.capture());
        verify(messageBoardRepository).save(messageBoardCaptor.capture());
        assertEquals(group, groupCaptor.getValue());
        assertEquals(group, messageBoardCaptor.getValue().getGroup());
        assertNotNull(result);
        assertEquals(group.getStartDate(), result.getStartDate());
        assertEquals(group.getGroupName(), result.getGroupName());
        assertEquals(group.getUsers().size(), result.getUserLeanOutputDtos().size());
        assertNotNull(result.getUserLeanOutputDtos());
        assertTrue(result.getUserLeanOutputDtos().size() > 0);
        assertEquals(group.getUsers().get(0).getId(), result.getUserLeanOutputDtos().get(0).getId());
    }

    @Test
//    @Disabled
        void createGroup_invalidDate(){
        //arrange
        GroupInputDto groupInputDto = new GroupInputDto();
        groupInputDto.setStartDate(LocalDate.of(2025, 5, 23));
        groupInputDto.setEndDate(LocalDate.of(2024, 6, 27));
        groupInputDto.setGroupName("Nog een geweldige coole naam");
        groupInputDto.setUsers(Arrays.asList(5L, 6L));
        Group group = new Group();

        BadRequestException exception =  assertThrows(BadRequestException.class, () -> {
            groupServiceImpl.createGroup(groupInputDto);
        });
            assertEquals("Start date cannot be after end date", exception.getMessage());
    }
}



