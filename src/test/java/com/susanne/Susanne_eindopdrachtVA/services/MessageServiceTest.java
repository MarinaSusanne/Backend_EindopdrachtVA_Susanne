package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.BadRequestException;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageServiceTest {

    @Mock
    MessageRepository messageRepository;
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


        message1 = new Message();
        message1.setId(1L);
        message1.setContent("Inhoud van een berichtje dat heel leuk is!");
        message1.setSubmitDate(LocalDateTime.now());
        message1.setUser(user1);


        message2 = new Message();
        message2.setId(2L);
        message2.setContent("Dit is een tweede testbericht!");
        message2.setSubmitDate(LocalDateTime.of(2023, 6, 13, 11, 13));
        message2.setUser(user2);


        message3 = new Message();
        message3.setId(3L);
        message3.setContent("Dit is een derde testbericht!");
        message3.setSubmitDate(LocalDateTime.of(2023, 6, 14, 8, 34));
        message3.setUser(user3);


        message4 = new Message();
        message4.setId(4L);
        message4.setContent("Dit is een vierde testbericht!");
        message4.setSubmitDate(LocalDateTime.of(2023, 6, 15, 7, 5));
        message4.setUser(user1);


        message5 = new Message();
        message5.setId(5L);
        message5.setContent("Dit is een vijfde testbericht!");
        message5.setSubmitDate(LocalDateTime.of(2023, 6, 16, 15, 56));
        message5.setUser(user2);

        messages1 = Arrays.asList(message4, message3);
        messages2 = Arrays.asList(message2, message5);
        messages3 = Arrays.asList(message1);

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
        user1.setMessages(messages1);
        user1.setGroup(group1);
        user2.setMessages(messages2);
        user2.setGroup(group2);
        user3.setMessages(messages3);
        user3.setGroup(group1);

        group1.setMessageBoard(messageBoard1);
        group2.setMessageBoard(messageBoard2);

        message1.setMessageBoard(messageBoard1);
        message2.setMessageBoard(messageBoard2);
        message3.setMessageBoard(messageBoard1);
        message4.setMessageBoard(messageBoard1);
        message5.setMessageBoard(messageBoard2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
//    @Disabled
    void getAllMessages() {
        //Arrange
        List<Message> messages = List.of(message1, message2, message3, message4, message5);
        when(messageRepository.findAll()).thenReturn(messages);

        //Act
        List<MessageOutputDto> result = messageService.getAllMessages();

        // Assert
        assertEquals(message1.getContent(), result.get(0).getContent());
        assertEquals(message2.getSubmitDate(), result.get(1).getSubmitDate());
        assertEquals(message3.getId(), result.get(2).getId());
        assertEquals(message5.getContent(), result.get(4).getContent());
    }


    @Test
//    @Disabled
    void createAndAssignMessage() {
        //arrange
        MessageInputDto messageInputDto = new MessageInputDto();
        messageInputDto.setContent(message1.getContent());
        messageInputDto.setUserId(user1.getId());
        messageInputDto.setSubmitDate(message1.getSubmitDate());

        //act
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(messageRepository.save(message1)).thenReturn(message1);
        messageService.createAndAssignMessage(1L, messageInputDto);

        //assert
        verify(messageRepository).save(captor.capture());
        Message message = captor.getValue();
        assertEquals(message1.getMessageBoard(), message.getMessageBoard());
        assertEquals(message1.getUser(), message.getUser());
    }

    @Test
//    @Disabled
    void deleteMessage() {
        when(messageRepository.existsById(message2.getId())).thenReturn(true);
        // Act
        messageService.deleteMessage(2L);

        // Assert
        verify(messageRepository, times(1)).deleteById(message2.getId());
        verify(messageRepository, times(1)).existsById(2L);

        // Controleer of het bericht daadwerkelijk is verwijderd
        assertFalse(messageRepository.findById(message2.getId()).isPresent());
    }

    @Test
//    @Disabled
    void testDeleteMessageWithNonexistentId() {
        //arrange
        Long messageId = 108L; // Niet-bestaand ID
        when(messageRepository.existsById(messageId)).thenReturn(false);
        //act
        assertThrows(RecordNotFoundException.class, () -> messageService.deleteMessage(messageId));
        //assert
        verify(messageRepository, never()).deleteById(messageId);
    }

    @Test
//    @Disabled
        void testUpdateMessage() {
            //arrange
            MessageInputDto messageInputDto = new MessageInputDto();
            messageInputDto.setContent("Ik pas een berichtje aan ");
            messageInputDto.setUserId(user1.getId());

            when(messageRepository.findById(message4.getId())).thenReturn(Optional.of(message4));
            when(messageRepository.save(any())).thenReturn(message4);
            //act
            messageService.updateMessage(4L, messageInputDto);

            //assert
            ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
            verify(messageRepository).save(captor.capture());
            Message captured = captor.getValue();
            assertEquals(message4.getContent(), captured.getContent());
            assertEquals(message4.getSubmitDate(), captured.getSubmitDate());
            assertEquals(message4.getId(), captured.getId());
        }

        @Test
//        @Disabled
        void testUpdateMessage2() {
           //arrange
            MessageInputDto messageInputDto = new MessageInputDto();
            messageInputDto.setContent("Ik pas weer lekker een berichtje aan");

            when(messageRepository.save((Message) any())).thenThrow(new BadRequestException("An error occurred"));
            when(messageRepository.findById((Long) any())).thenReturn(Optional.of(new Message()));
            //act
            assertThrows(BadRequestException.class, () -> messageService.updateMessage(1L, messageInputDto));
            //assert
            verify(messageRepository).save((Message) any());
            verify(messageRepository).findById((Long) any());
        }
}
