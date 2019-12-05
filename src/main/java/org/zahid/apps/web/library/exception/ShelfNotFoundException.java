package org.zahid.apps.web.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShelfNotFoundException extends RuntimeException {
    public ShelfNotFoundException(final String message) {
        super(message);
    }
}
