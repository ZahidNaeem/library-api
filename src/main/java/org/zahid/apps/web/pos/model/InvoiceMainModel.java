package org.zahid.apps.web.pos.model;

import lombok.*;
import org.zahid.apps.web.pos.entity.Auditable;
import org.zahid.apps.web.pos.entity.InvoiceMain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceMainModel extends Auditable<Long> implements Serializable {

private static final long serialVersionUID = 1L;

  private Long invNum;

  private Date invDate;

  private String invType;

  private BigDecimal paidAmt;

  private String reason;

  private String remarks;

  private Long refInvoice;

  private List<InvoiceMain> invoiceMains;

  private List<InvoiceDtlModel> invoiceDtls;

  private Long party;
}
