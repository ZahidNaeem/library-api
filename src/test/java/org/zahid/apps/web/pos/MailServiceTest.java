package org.zahid.apps.web.pos;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zahid.apps.web.pos.model.GmailCredentials;
import org.zahid.apps.web.pos.service.GmailService;
import org.zahid.apps.web.pos.service.impl.GmailServiceImpl;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MailServiceTest {

 @Autowired
 private GmailService gmailService;

    @DisplayName("Send email")
    @Test
    void sendMail() {
        /*try {
            gmailService.sendMessage("Test Email", "To reset you account password, please click on below link:\nhttp://localhost:3000", "hzahidnaeem@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        mailService.sendEmail("Test Email", "Test", "hzahidnaeem@gmail.com");
    }
}
