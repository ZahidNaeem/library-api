package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_VOUCHER_HEADER database table.
 */
@Entity
@Table(name = "XXIM_VOUCHER_HEADER")
@JsonIdentityInfo(scope = VoucherHeader.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "headerId")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name = "VoucherHeader.findAll", query = "SELECT v FROM VoucherHeader v")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherHeader extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*@SequenceGenerator(name = "XXIM_VOUCHER_HEADER_HEADERID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_VOUCHER_HEADER_HEADERID_GENERATOR")*/
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HEADER_ID")
    private Long headerId;

    private String remarks;

    @Temporal(TemporalType.DATE)
    @Column(name = "VOUCHER_DATE")
    private Date voucherDate;

    @Column(name = "VOUCHER_TYPE")
    private String voucherType;

    // bi-directional many-to-one association to Party
    @ManyToOne
    @JoinColumn(name = "PARTY_CODE")
    private Party party;

    // bi-directional many-to-one association to VoucherLine
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "voucherHeader")
    private List<VoucherLine> voucherLines;
}