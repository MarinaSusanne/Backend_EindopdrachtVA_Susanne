package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.*;

@Entity
@Table(name="assignments")

public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String info;


}

//TODO: hoe ga ik de opdrachten bewaren? In database of computer?
//TODO: relatie met een groep (many to many)
//TODO: relatie met user (many to many)
//TODO: unieke filename aanmaken op basis van user en tijd, zodat dit lokaal goed gaat (mezelf niet overschrijf)

