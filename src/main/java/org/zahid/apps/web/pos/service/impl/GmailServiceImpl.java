package org.zahid.apps.web.pos.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.zahid.apps.web.pos.model.GmailCredentials;
import org.zahid.apps.web.pos.service.GmailService;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class GmailServiceImpl implements GmailService {

    private static final String APPLICATION_NAME = "pos";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final HttpTransport httpTransport;
    private GmailCredentials gmailCredentials;

    public GmailServiceImpl(final HttpTransport httpTransport, final GmailCredentials gmailCredentials) {
        this.httpTransport = httpTransport;
        this.gmailCredentials = gmailCredentials;
    }

    /*@Override
    public void setGmailCredentials(final GmailCredentials gmailCredentials) {
        this.gmailCredentials = gmailCredentials;
    }*/

    @Override
    public boolean sendMessage(final String subject, final String body, final String... recipientAddress) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(
                createEmail(gmailCredentials.getUserEmail(), subject, body, recipientAddress));

        return createGmail().users()
                .messages()
                .send(gmailCredentials.getUserEmail(), message)
                .execute()
                .getLabelIds().contains("SENT");
    }

    private Gmail createGmail() {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private MimeMessage createEmail(final String from, final String subject, final String bodyText, final String... to) throws MessagingException {
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        final Address[] addresses = new Address[to.length];
        for (int i = 0; i < to.length; i++) {
            addresses[i] = new InternetAddress(to[i]);
        }
        email.setFrom(new InternetAddress(from));
        email.addRecipients(javax.mail.Message.RecipientType.TO, addresses);
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);

        return new Message()
                .setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
    }

    private Credential authorize() {
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(gmailCredentials.getClientId(), gmailCredentials.getClientSecret())
                .build()
                .setAccessToken(gmailCredentials.getAccessToken())
                .setRefreshToken(gmailCredentials.getRefreshToken());
    }
}
