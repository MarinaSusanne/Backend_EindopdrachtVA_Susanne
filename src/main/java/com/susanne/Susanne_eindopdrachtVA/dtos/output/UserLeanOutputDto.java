package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserLeanOutputDto {
        private Long id;
        private String firstName;
        private String lastName;
        private byte[] photo;
    }

