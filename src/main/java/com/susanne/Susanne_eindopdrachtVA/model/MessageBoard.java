package com.susanne.Susanne_eindopdrachtVA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.validator.internal.util.logging.Messages;

import java.util.List;

@Entity
@Table(name="message_boards")
public class MessageBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardInfo;

    @OneToMany(mappedBy = "messageBoard")
    @JsonIgnore
    private List<Message> messages;

    @OneToOne
    private Group group;

    public MessageBoard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardInfo() {
        return boardInfo;
    }

    public void setBoardInfo(String boardInfo) {
        this.boardInfo = boardInfo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessagetoMessageList (Message message){this.messages.add(message);}

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


}


