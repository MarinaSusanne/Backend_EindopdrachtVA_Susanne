package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    public enum UserRole {
        USER, ADMIN
    }

}

