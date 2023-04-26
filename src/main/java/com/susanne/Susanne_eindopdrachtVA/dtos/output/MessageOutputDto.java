package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class MessageOutputDto {
    private Long id;
    private String content;
    private LocalDateTime submitDate;
}
