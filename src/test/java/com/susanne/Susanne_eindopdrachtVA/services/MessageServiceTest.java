package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.hibernate.validator.internal.util.logging.Messages;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageServiceTest {

    @Mock
    MessageRepository messageRepository;

    @Mock
    MessageMapper messageMapper;

    @Mock
    MessageBoardRepository messageBoardRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    MessageService messageService;

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
    List<User> users1;
    MessageBoard messageBoard1;
    MessageBoard messageBoard2;
    User user1;
    User user2;
    User user3;
    Group group1;
    Group group2;


    @BeforeEach
    void setUp() {
        user1 = new User ();
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
        user1.setDateOfBirth(LocalDate.of(1999,6,8));
        user1.setMessages(messages1);
        user1.setGroup(group1);

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
        user2.setMessages(messages2);
        user2.setGroup(group2);

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
        user3.setMessages(messages3);
        user3.setGroup(group1);

        group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Naam van Groep");
        group1.setStartDate(LocalDate.of(2023,5,29));
        group1.setEndDate(LocalDate.of(2023,12,22));
        group1.setGroupInfo("groepinfo die vet leuk is");
        group1.setUsers(users1);
        group1.setMessageBoard(messageBoard2);

        group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Naam van Groep 2");
        group2.setStartDate(LocalDate.of(2023,5,29));
        group2.setEndDate(LocalDate.of(2023,12,29));
        group2.setGroupInfo("groepinfo die nog veel leuker is");
//        group2.setUsers(users2);
        group2.setMessageBoard(messageBoard2);

        message1 = new Message(1L, "Inhoud van een berichtje dat heel leuk is!", LocalDate.of(2023,06,12), user1, messageBoard1);
        message2 = new Message(2L, "Dit is een tweede testbericht!", LocalDate.of(2023, 06, 13), user2, messageBoard2);
        message3 = new Message(3L, "Dit is een derde testbericht!", LocalDate.of(2023, 06, 14), user3, messageBoard1);
        message4 = new Message(4L, "Dit is een vierde testbericht!", LocalDate.of(2023, 06, 15), user1, messageBoard1);
        message5 = new Message(5L, "Dit is een vijfde testbericht!", LocalDate.of(2023, 06, 16), user2, messageBoard2);

        messages1 = Arrays.asList(message1, message4);
        messages2 = Arrays.asList(message2, message5);
        messages3 = Arrays.asList(message3);
        messageBoard1 = new MessageBoard(1L, "Dit is info over het prikbord van groep 1", messages1, users1, group1);
        messageBoard2 = new MessageBoard(2L, "Dit is info over het prikbord van groep 2", Arrays.asList(message2, message5), Arrays.asList(user2), group2);
        users1 = Arrays.asList(user1, user3);
        }


    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getAllMessages() {
        when(messageRepository.findAll()).thenReturn(List.of(message1, message2, message3, message4, message5));

        List<Message> messagesFound = messageService.messageMapper.messageToMessageDto()



    }

    @Test
    @Disabled
    void createAndAssignMessage() {
    }

    @Test
    @Disabled
    void deleteMessage() {
    }

    @Test
    @Disabled
    void updateMessage() {
    }
}