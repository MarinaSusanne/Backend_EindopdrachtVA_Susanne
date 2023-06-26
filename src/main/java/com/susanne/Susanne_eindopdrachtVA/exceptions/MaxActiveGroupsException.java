package com.susanne.Susanne_eindopdrachtVA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class MaxActiveGroupsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MaxActiveGroupsException() {
        super();
    }

    public MaxActiveGroupsException(String message) {
        super(message);
    }

}

