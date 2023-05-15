package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.output.MessageOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
import com.susanne.Susanne_eindopdrachtVA.model.Message;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
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

import static org.junit.jupiter.api.Assertions.*;

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
    ArgumentCaptor<MessageBoard> captor;
    Message message1;


    @BeforeEach
    void setUp() {
        message1 = new Message (1L, "Inhoud van een berichtje die heel leuk is!", LocalDate.of(2023,06,12), userLeanOuputDto1 )
        userLeanOutputDto1 = new UserLeanOutputDto(1L, "Jantje", "Jan");
        userleanOutputDto2 = new UserLeanOutputDto(2L, "Klaasje", "Klaas");
        messageOutputDto1 = new MessageOutputDto(1L,"Inhoud van een berichtje", LocalDate.of(2023,08,12),)
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getMessagesFromBoard() {

    }

    @Test
    @Disabled
    void updateMessageBoard() {
    }
}