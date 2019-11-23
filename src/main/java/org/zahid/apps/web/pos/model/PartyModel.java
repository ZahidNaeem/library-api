package org.zahid.apps.web.pos.model;

import lombok.*;
import org.zahid.apps.web.pos.entity.Auditable;
import org.zahid.apps.web.pos.entity.VoucherHeader;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyModel extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long partyCode;
    private String address;
    private String cellNo;
    private String contactPerson;
    private Date effectiveEndDate;
    private Date effectiveStartDate;
    private String email;
    private String fax;
    private String ntn;
    private String partyName;
    private String partyOwner;
    private String partyType;
    private String phone;
    private String strn;
    private String web;
    private List<InvoiceMainModel> invoiceMains;
    private List<PartyBalanceModel> partyBalances;
    private List<VoucherHeader> voucherHeaders;
}
