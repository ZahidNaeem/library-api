package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the XXIM_VOUCHER_LINE database table.
 */
@Entity
@Table(name = "XXIM_VOUCHER_LINE")
@JsonIdentityInfo(scope = VoucherLine.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "lineId")
@NamedQuery(name = "VoucherLine.findAll", query = "SELECT v FROM VoucherLine v")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherLine extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*@SequenceGenerator(name = "XXIM_VOUCHER_LINE_LINEID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_VOUCHER_LINE_LINEID_GENERATOR")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LINE_ID")
    private Long lineId;

    private BigDecimal amount;

    @Column(name = "AMOUNT_MODE")
    private String amountMode;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHEQUE_DATE")
    private Date chequeDate;

    @Column(name = "CHEQUE_NUM")
    private String chequeNum;

    private String remarks;

    // bi-directional many-to-one association to VoucherHeader
    @ManyToOne
    @JoinColumn(name = "HEADER_ID")
    private VoucherHeader voucherHeader;
}