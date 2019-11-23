package org.zahid.apps.web.pos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zahid.apps.web.pos.service.InvoiceMainService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InvoiceMainServiceTest {
    @Autowired
    private InvoiceMainService invoiceMainService;

    @DisplayName("Testing item service")
    @Test
    void test() {
        /*invoiceMainService.findAllPOs().forEach(po -> {
            System.out.println(po.toString());
        });*/
        /*invoiceMainService.findParties().forEach(party -> {
            System.out.println(party.toString());
        });*/
    }
}
