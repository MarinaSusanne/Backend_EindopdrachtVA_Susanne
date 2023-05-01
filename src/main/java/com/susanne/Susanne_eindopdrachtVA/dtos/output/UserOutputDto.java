package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserOutputDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String streetName;
    private String houseNumber;
    private String zipcode;
    private String city;
    private LocalDate dateOfBirth;
    private byte[] photo;
}
