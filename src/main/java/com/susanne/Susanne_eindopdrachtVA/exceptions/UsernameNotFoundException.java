package com.susanne.Susanne_eindopdrachtVA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameNotFoundException  extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }
}