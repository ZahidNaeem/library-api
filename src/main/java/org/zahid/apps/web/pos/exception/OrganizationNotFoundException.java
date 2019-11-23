package org.zahid.apps.web.pos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(final String message) {
        super(message);
    }
}
