package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class MessageInputDto {
    private Long id;

    @NotNull(message = "Content is required")
    @Size (min = 200, message = "content must be less than 500 characters")
    private String content;

    private LocalDate submitDate;

    @PrePersist
    public void prePersist() {
        this.submitDate = LocalDate.now();
    }
}
