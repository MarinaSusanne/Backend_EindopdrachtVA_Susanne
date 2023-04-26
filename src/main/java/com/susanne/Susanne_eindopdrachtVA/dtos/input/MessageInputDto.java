package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class MessageInputDto {


    @NotNull(message = "Content is required")
    @Size (max = 500, message = "content must be less than 500 characters")
    private String content;

    private LocalDateTime submitDate;

}
