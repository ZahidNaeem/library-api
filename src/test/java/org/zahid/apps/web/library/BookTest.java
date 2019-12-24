package org.zahid.apps.web.library;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zahid.apps.web.library.model.BookModel;
import org.zahid.apps.web.library.service.BookService;

import java.util.List;

//import org.assertj.core.api.Assertions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookTest {

//    @Autowired
//    private BookService bookService;
//
//    @DisplayName("Search book by author, subject, publisher and researcher")
//    @Test
//    void searchBook() {
//        final List<BookModel> bookModels = bookService.searchByCriteria(2, 7, null, 63);
//        final int size = bookModels.size();
//        Assert.assertEquals(1, size);
//    }
}
