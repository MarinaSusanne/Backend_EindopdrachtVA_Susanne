package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.susanne.Susanne_eindopdrachtVA.model.Authority;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserPutInputDto {

        private String username;

        private String password;

        private String email;

        private String firstName;

        private String lastName;

        private String streetName;

        private String houseNumber;

        private String zipcode;

        private String city;

        @JsonFormat(pattern="yyyy-MM-dd")
        private LocalDate dateOfBirth;

        private byte[] photo;

        public Boolean enabled;

        public String apikey;

        @JsonSerialize
        public Set<Authority> authorities;

}



