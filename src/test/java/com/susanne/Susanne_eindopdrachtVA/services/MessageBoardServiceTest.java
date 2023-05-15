package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.mappers.MessageMapper;
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

    @BeforeEach
    void setUp() {
        //arrange
        //act
        //assert
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