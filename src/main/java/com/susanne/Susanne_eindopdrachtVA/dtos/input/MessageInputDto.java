package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class MessageInputDto {

    @NotBlank(message = "Content is required")
    @Size (max = 500, message = "content must be less than 500 characters")
    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitDate;

    private Long userId;

    //for admin to add a message to messageboard ofthe page it's currently in, cause it's not in a groupId
    private Long groupId;

   }
