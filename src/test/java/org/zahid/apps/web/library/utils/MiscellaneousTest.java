package org.zahid.apps.web.library.utils;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MiscellaneousTest {

  @DisplayName("Record Exists")
  @Test
  void recordExists() {
    final Integer actual = 1;
    Assert.assertEquals(Miscellaneous.exists("SUBJECT", "SUBJECT_ID", 6L), actual);
  }
}
