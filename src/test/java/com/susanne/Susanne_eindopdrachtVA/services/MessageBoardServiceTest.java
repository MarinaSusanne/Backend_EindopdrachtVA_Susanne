package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.MessageBoardInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageBoardOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageRepository;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MessageBoardServiceTest {

    @Mock
    MessageBoardRepository messageBoardRepository;

    @Mock
    MessageRepository messageRepository;

    @Mock
    MessageMapper messageMapper;

    @InjectMocks
    MessageBoardService messageBoardService;

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
        message1.setSubmitDate(LocalDateTime.of(2023, 6, 12, 12, 36));
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
    @Disabled
    void getMessagesFromBoard() {
        // Arrange
        when(messageBoardRepository.findById(messageBoard1.getId())).thenReturn(Optional.of(messageBoard1));
        List<MessageOutputDto> expectedOutput = new ArrayList<>();
        for (Message message : messageBoard1.getMessages()) {
            MessageOutputDto messageOutputDto = MessageMapper.messageToMessageDto(message);
            expectedOutput.add(messageOutputDto);
        }
              // Act
        List<MessageOutputDto> actualOutput = messageBoardService.getMessagesFromBoard(messageBoard1.getId());

        // Assert
        assertEquals(expectedOutput.size(), actualOutput.size());
        assertEquals(expectedOutput.get(0).getContent(), actualOutput.get(0).getContent());
        assertEquals(expectedOutput.get(0).getSubmitDate(), actualOutput.get(0).getSubmitDate());
        assertEquals(expectedOutput.get(1).getContent(), actualOutput.get(1).getContent());
    }


    @Test
    void testGetMessagesFromBoardWithNonExistentId() {
        //arrange
        when(messageBoardRepository.findById(any())).thenReturn(Optional.empty());
        //act & assert
        assertThrows(RecordNotFoundException.class, () -> messageBoardService.getMessagesFromBoard(101L));
    }


    @Test
    @Disabled
    void updateMessageBoardInfo() {
       //arrange
        String newBoardInfo = "Nieuwe informatie over het prikbord van groep 1";
        MessageBoardInputDto updatedMessageBoardDto = new MessageBoardInputDto();
        updatedMessageBoardDto.setBoardInfo(newBoardInfo);
        when(messageBoardRepository.findById(1L)).thenReturn(Optional.of(messageBoard1));
        when(messageBoardRepository.save(any(MessageBoard.class))).thenReturn(messageBoard1);

        // act
        MessageBoardService messageBoardService = new MessageBoardService(messageBoardRepository);
        MessageBoardOutputDto result = messageBoardService.updateMessageBoardInfo(1L, updatedMessageBoardDto);

        //verify and assert
        verify(messageBoardRepository, times(1)).findById(1L);
        verify(messageBoardRepository, times(1)).save(any(MessageBoard.class));
        assertEquals(messageBoard1.getId(), result.getId());
        assertEquals(updatedMessageBoardDto.getBoardInfo(), result.getBoardInfo());
    }

    @Test
    void testUpdateMessageBoard2() {
        when(messageBoardRepository.save((MessageBoard) any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));
        when(messageBoardRepository.findById((Long) any())).thenReturn(Optional.of(new MessageBoard()));

        MessageBoardInputDto messageBoardInputDto = new MessageBoardInputDto();
        messageBoardInputDto.setBoardInfo("Board Info");
        assertThrows(RecordNotFoundException.class,
                () -> messageBoardService.updateMessageBoardInfo(112L, messageBoardInputDto));
        verify(messageBoardRepository).save((MessageBoard) any());
        verify(messageBoardRepository).findById((Long) any());

    }
}