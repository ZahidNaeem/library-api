package org.zahid.apps.web.library.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zahid.apps.web.library.repo.AuthorRepo;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith((SpringExtension.class))
class AuthorServiceImplTest {

    @Mock
    private AuthorRepo authorRepo;


    @Test
    void deleteById() {
    }
}