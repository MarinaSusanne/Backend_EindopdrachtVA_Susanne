package com.susanne.Susanne_eindopdrachtVA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    private String groupInfo;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<User> users;

    @OneToOne
    private MessageBoard messageBoard;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<HomeworkAssignment> homeworkAssignments;

    public Group() {
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(String groupInfo) {
        this.groupInfo = groupInfo;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User admin) {
        this.users.add(admin);
    }

    public MessageBoard getMessageBoard() {
        return messageBoard;
    }

    public void setMessageBoard(MessageBoard messageBoard) {
        this.messageBoard = messageBoard;
    }

    public List<HomeworkAssignment> getHomeworkAssignments() {
        return homeworkAssignments;
    }

    public void setHomeworkAssignments(List<HomeworkAssignment> homeworkAssignments) {
        this.homeworkAssignments = homeworkAssignments;
    }
}

