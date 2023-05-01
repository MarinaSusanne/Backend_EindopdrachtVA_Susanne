package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UserInputDto {

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String streetName;

    private String houseNumber;

    private String zipcode;

    private String city;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private byte[] photo;
}

//TODO: toevoegen extra annotaties
