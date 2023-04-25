package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table (name="groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int groupNumber;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;

    private String groupInfo;

}


//TODO: relatie met groepsleden (wat doe ik dan met een admin, alles is one to many, behalve de admin, die is manytomany