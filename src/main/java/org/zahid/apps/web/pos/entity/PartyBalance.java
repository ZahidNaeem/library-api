package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the XXIM_PARTY_BALANCE database table.
 */
@Entity
@Table(name = "XXIM_PARTY_BALANCE")
@JsonIdentityInfo(scope = PartyBalance.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "partyBalanceId")
@NamedQueries({
    @NamedQuery(name = "PartyBalance.findAll", query = "SELECT p FROM PartyBalance p"),
    @NamedQuery(name = "PartyBalance.generateID", query = "SELECT coalesce(max(partyBalanceId), 0) + 1 FROM PartyBalance p")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyBalance extends Auditable<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
//    @SequenceGenerator(name = "XXIM_PARTY_BALANCE_PARTYBALANCEID_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_PARTY_BALANCE_PARTYBALANCEID_GENERATOR")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PARTY_BALANCE_ID")
  private Long partyBalanceId;

  @Column(name = "AMOUNT")
  private BigDecimal amount;

  @Temporal(TemporalType.DATE)
  @Column(name = "PARTY_BALANCE_DATE")
  private Date partyBalanceDate;

  private String remarks;

  // bi-directional many-to-one association to Party
  @ManyToOne
//    @NotFound(action = NotFoundAction.IGNORE)
  @JoinColumn(name = "PARTY_CODE")
  private Party party;
}