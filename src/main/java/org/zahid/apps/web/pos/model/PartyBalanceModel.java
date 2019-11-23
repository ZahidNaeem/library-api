package org.zahid.apps.web.pos.model;

import lombok.*;
import org.zahid.apps.web.pos.entity.Auditable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyBalanceModel  extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long partyBalanceId;

    private BigDecimal amount;

    private Date partyBalanceDate;

    private String remarks;

    private Long party;
}