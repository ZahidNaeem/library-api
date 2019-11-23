package org.zahid.apps.web.pos.exception;

public class PartyBalanceNotFoundException extends RuntimeException {
    public PartyBalanceNotFoundException(final String message) {
        super(message);
    }
}
