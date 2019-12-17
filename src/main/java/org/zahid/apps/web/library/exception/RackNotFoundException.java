package org.zahid.apps.web.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RackNotFoundException extends RuntimeException {
    public RackNotFoundException(final String message) {
        super(message);
    }
}