package com.susanne.Susanne_eindopdrachtVA.integrationtests;

import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import com.susanne.Susanne_eindopdrachtVA.repository.GroupRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.MessageBoardRepository;
import com.susanne.Susanne_eindopdrachtVA.repository.UserRepository;
import com.susanne.Susanne_eindopdrachtVA.services.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GroupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
   private  MessageBoardRepository messageBoardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
   private UserRepository userRepository;

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
     List<User> usersList1;
     List<User> usersList2;
     List<User> usersList3;

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
        usersList2 = Arrays.asList(user2, user4);
        usersList3 = Arrays.asList(user5);

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
        group4.setUsers(usersList3);

    //Hier leg ik in de relaties die ik niet hierboven al kan maken
        user1.setGroup(group1);
        user2.setGroup(group2);
        user3.setGroup(group1);
        user4.setGroup(group2);
        user5.setGroup(group4);

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);
        user4 = userRepository.save(user4);
        user5 = userRepository.save(user5);
        user6 = userRepository.save(user6);

        group1 = groupRepository.save(group1);
        group2 = groupRepository.save(group2);
        group3 = groupRepository.save(group3);
        group4 = groupRepository.save(group4);
    }

    @Test
    @Disabled
    void getUsersByGroupId() {
    }

    @Test
    @Disabled
    void getMyGroup() throws Exception {
        GroupOutputDto expectedGroupOutputDto = new GroupOutputDto();
        expectedGroupOutputDto.setId(group1.getId());
        expectedGroupOutputDto.setGroupName(group1.getGroupName());

        mockMvc.perform(get("/users/" + user1.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(group1.getId()))
                .andExpect(jsonPath("id").value(expectedGroupOutputDto.getId()))
                .andExpect(jsonPath("groupName").value("Naam van Groep")
                .andExpect(jsonPath("startDate").value("2023-5-12")
                .andExpect(jsonPath("endDate").value(group1.getEndDate().toString()))
                .andExpect(jsonPath("groupInfo").value(group1.getGroupInfo()))

        //TODO: waarom doet hij het niet en hoe ga ik om met een DTO in een DTO? dto

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
