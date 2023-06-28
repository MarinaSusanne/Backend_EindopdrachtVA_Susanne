package com.susanne.Susanne_eindopdrachtVA.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.MessageBoard;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GroupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MessageBoardRepository messageBoardRepository;

    @Autowired
    UserRepository userRepository;

    User user1;
    User user2;
    User user3;
    User user4;
    User user5;

    User user6;
    Group group1;
    Group group2;
    MessageBoard messageBoard1;
    MessageBoard messageBoard2;
    MessageBoard messageBoard3;
    List<User> usersList1;
    List<User> usersList2;
    List<User> usersList3;
    GroupInputDto groupInputDto4;

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

        User user6 = new User();
        user6.setId(6L);
        user6.setUsername("TestGebruiker6");
        user6.setEmail("gebruiker6@example.com");
        user6.setPassword("wachtwoord789");
        user6.setFirstName("Emma");
        user6.setLastName("Jansen");
        user6.setStreetName("Hoofdstraat");
        user6.setHouseNumber("10");
        user6.setZipcode("5678 CD");
        user6.setCity("Amsterdam");
        user6.setDateOfBirth(LocalDate.of(1995, 8, 20));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);

        usersList1 = Arrays.asList(user1, user3);
        usersList2 = Arrays.asList(user2, user4);
        usersList3 = Arrays.asList(user5, user6);

        messageBoard1 = new MessageBoard();
        messageBoard1.setId(1L);

        messageBoard2 = new MessageBoard();
        messageBoard2.setId(2L);

        messageBoard3 = new MessageBoard();
        messageBoard3.setId(3L);

        messageBoardRepository.save(messageBoard1);
        messageBoardRepository.save(messageBoard2);
        messageBoardRepository.save(messageBoard3);

        group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Naam van Groep");
        group1.setStartDate(LocalDate.of(2023, 5, 12));
        group1.setEndDate(LocalDate.of(2023, 12, 22));
        group1.setGroupInfo("groepinfo die vet leuk is");
        group1.setUsers(usersList1);
        group1.setMessageBoard(messageBoard1);

        group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Naam van Groep 2");
        group2.setStartDate(LocalDate.of(2023, 5, 13));
        group2.setEndDate(LocalDate.of(2023, 12, 29));
        group2.setGroupInfo("groepinfo die nog veel leuker is");
        group2.setMessageBoard(messageBoard2);
        group2.setUsers(usersList2);

        groupInputDto4 = new GroupInputDto();
        groupInputDto4.setGroupName("Naam van Groep 4");
        groupInputDto4.setStartDate(LocalDate.of(2022, 7, 29));
        groupInputDto4.setEndDate(LocalDate.of(2023, 8, 21));
        groupInputDto4.setGroupInfo("Groep die niet meer actief is");
        groupInputDto4.setUsers(usersList3.stream().map(User::getId).toList());

        //Here I add the relations
        user1.setGroup(group1);
        user2.setGroup(group2);
        user3.setGroup(group1);
        user4.setGroup(group2);

        groupRepository.save(group1);
        groupRepository.save(group2);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
    }

    @Test
//    @Disabled
    void getMyGroup() throws Exception {
        mockMvc.perform(get("/groups/users/{userId}/group", user1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(group1.getId()))
                .andExpect(jsonPath("groupName").value("Naam van Groep"))
                .andExpect(jsonPath("startDate").value("2023-05-12"))
                .andExpect(jsonPath("endDate").value("2023-12-22"))
                .andExpect(jsonPath("messageBoardId").value("1"))
                .andExpect(jsonPath("groupInfo").value("groepinfo die vet leuk is"))
                .andExpect(jsonPath("userPictureOutputDtos[0].id").value(user1.getId()))
                .andExpect(jsonPath("userPictureOutputDtos[1].id").value(user3.getId()));

    }

    @Test
//    @Disabled
    void getSpecificGroup() throws Exception {
        mockMvc.perform(get("/groups/admin/{groupId}", group2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(group2.getId()))
                .andExpect(jsonPath("groupName").value("Naam van Groep 2"))
                .andExpect(jsonPath("startDate").value("2023-05-13"))
                .andExpect(jsonPath("endDate").value("2023-12-29"))
                .andExpect(jsonPath("messageBoardId").value("2"))
                .andExpect(jsonPath("groupInfo").value("groepinfo die nog veel leuker is"))
                .andExpect(jsonPath("userPictureOutputDtos[0].id").value(user2.getId()))
                .andExpect(jsonPath("userPictureOutputDtos[1].id").value(user4.getId()));
    }

    @Test
    void createGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/groups/admin", groupInputDto4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(groupInputDto4)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("groupName").value("Naam van Groep 4"))
                .andExpect(jsonPath("startDate").value("2022-07-29"))
                .andExpect(jsonPath("endDate").value("2023-08-21"))
                .andExpect(jsonPath("groupInfo").value("Groep die niet meer actief is"));
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}