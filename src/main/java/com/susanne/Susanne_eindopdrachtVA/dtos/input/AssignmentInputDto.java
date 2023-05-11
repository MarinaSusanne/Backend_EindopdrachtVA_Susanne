package com.susanne.Susanne_eindopdrachtVA.dtos.input;

import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.Group;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.AbstractDecimalMinValidator;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class AssignmentInputDto {

    @NotNull
    private String assignmentName;

    @NotNull
    private String info;

    private Long authorId;

}
