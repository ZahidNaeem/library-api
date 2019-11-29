package org.zahid.apps.web.library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zahid.apps.web.library.repo.UserRepo;

import static junit.framework.TestCase.assertTrue;

//import org.assertj.core.api.Assertions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepo userRepo;

    @DisplayName("User exists by email")
    @Test
    void existsByEmail() {
        assertTrue(userRepo.existsByEmail("abc@xyz.com"));
    }
}
