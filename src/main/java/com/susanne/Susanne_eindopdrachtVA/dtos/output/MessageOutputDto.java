package com.susanne.Susanne_eindopdrachtVA.dtos.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.susanne.Susanne_eindopdrachtVA.model.User;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitDate;

    private UserLeanOutputDto userLeanOutputDto;
}
