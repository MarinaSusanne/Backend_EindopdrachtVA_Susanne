package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.*;

@Entity
@Table(name="messageboards")
public class MessageBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

//TODO: check of er ook een tabel moet voor messageboard
//TODO: relatie met groep (one to one)
//TODO: relatie met messages (one to many)