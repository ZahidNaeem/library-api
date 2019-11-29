package org.zahid.apps.web.library.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface GmailService {
//    void setGmailCredentials(final GmailCredentials gmailCredentials);

    boolean sendMessage(final String subject, final String body, final String... recipientAddress) throws MessagingException, IOException;
}
