package com.susanne.Susanne_eindopdrachtVA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoUsersWithoutGroupException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NoUsersWithoutGroupException() {
        super();
    }

    public NoUsersWithoutGroupException(String message) {
        super(message);
    }

}

