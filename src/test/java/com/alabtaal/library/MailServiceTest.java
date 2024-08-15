package com.alabtaal.library;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MailServiceTest {

 /*@Autowired
 private GmailService gmailService;

    @DisplayName("Send email")
    @Test
    void sendMail() {
        try {
            gmailService.sendMessage("Test Email", "To reset you account password, please click on below link:\nhttp://localhost:3000", "hzahidnaeem@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mailService.sendEmail("Test Email", "Test", "hzahidnaeem@gmail.com");
    }*/
}
