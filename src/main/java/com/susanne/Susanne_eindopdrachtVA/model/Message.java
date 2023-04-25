package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table (name="messages")

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate submitDate;

    public Message() {
    }
}


//TODO: relatie met auteur/user leggen (one to many)
//TODO: relatie met prikbord (one to many)
