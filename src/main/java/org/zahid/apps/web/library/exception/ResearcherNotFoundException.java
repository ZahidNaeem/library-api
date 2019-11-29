package org.zahid.apps.web.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResearcherNotFoundException extends RuntimeException {
    public ResearcherNotFoundException(final String message) {
        super(message);
    }
}
