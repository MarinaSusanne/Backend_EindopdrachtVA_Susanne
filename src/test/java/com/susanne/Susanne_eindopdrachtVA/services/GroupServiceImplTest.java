package com.susanne.Susanne_eindopdrachtVA.services;

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
import java.util.List;

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
    ArgumentCaptor<Message> captor;
    Message message1;
    Message message2;
    Message message3;
    Message message4;
    Message message5;
    List<Message> messages1;
    List<Message> messages2;
    List<Message> messages3;
    List<User> usersList1;
    List<User> usersList2;
    MessageBoard messageBoard1;
    MessageBoard messageBoard2;
    User user1;
    User user2;
    User user3;
    Group group1;
    Group group2;


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

        group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Naam van Groep");
        group1.setStartDate(LocalDate.of(2023, 5, 29));
        group1.setEndDate(LocalDate.of(2023, 12, 22));
        group1.setGroupInfo("groepinfo die vet leuk is");
        group1.setUsers(usersList1);

        usersList1 = Arrays.asList(user1, user3);
        usersList2 = Arrays.asList(user2);

        group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Naam van Groep 2");
        group2.setStartDate(LocalDate.of(2023, 5, 29));
        group2.setEndDate(LocalDate.of(2023, 12, 29));
        group2.setGroupInfo("groepinfo die nog veel leuker is");
        group2.setUsers(usersList2);

        messageBoard1 = new MessageBoard();
        messageBoard1.setId(1L);
        messageBoard1.setBoardInfo("Dit is info over het prikbord van groep 1");
        messageBoard1.setMessages(messages1);
        messageBoard1.setGroup(group1);

        messageBoard2 = new MessageBoard();
        messageBoard2.setId(2L);
        messageBoard2.setBoardInfo("Dit is info over het prikbord van groep 2");
        messageBoard2.setMessages(messages2);
        messageBoard2.setGroup(group2);

        //Hier leg ik in de relaties die ik niet hierboven al kan maken
        user1.setGroup(group1);
        user2.setGroup(group2);
        user3.setGroup(group1);

        group1.setMessageBoard(messageBoard1);
        group2.setMessageBoard(messageBoard2);

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