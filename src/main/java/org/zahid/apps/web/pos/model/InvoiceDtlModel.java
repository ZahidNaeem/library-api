package org.zahid.apps.web.pos.model;

import lombok.*;
import org.zahid.apps.web.pos.entity.Auditable;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDtlModel extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long invDtlNum;

    private BigDecimal itemPrice;

    private BigDecimal qnty;

    private Long invoiceMain;

    private Long item;
}
