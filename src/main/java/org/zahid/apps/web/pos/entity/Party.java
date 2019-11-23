package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_PARTIES database table.
 */
@Entity
@Table(name = "XXIM_PARTIES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PARTY_NAME"})
})
@JsonIdentityInfo(scope = Party.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "partyCode")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
        @NamedQuery(name = "Party.findAll", query = "SELECT p FROM Party p"),
        @NamedQuery(name = "Party.generateID", query = "SELECT coalesce(max(partyCode), 0) + 1 FROM Party p")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    @SequenceGenerator(name = "XXIM_PARTIES_PARTYCODE_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_PARTIES_PARTYCODE_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARTY_CODE")
    private Long partyCode;

    private String address;

    @Column(name = "CELL_NO")
    private String cellNo;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_END_DATE")
    private Date effectiveEndDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_START_DATE")
    private Date effectiveStartDate;

    private String email;

    private String fax;

    private String ntn;

    @Column(name = "PARTY_NAME")
    private String partyName;

    @Column(name = "PARTY_OWNER")
    private String partyOwner;

    @Column(name = "PARTY_TYPE")
    private String partyType;

    private String phone;

    private String strn;

    private String web;

    // bi-directional many-to-one association to InvoiceMain
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "party")
    private List<InvoiceMain> invoiceMains;

    // bi-directional many-to-one association to PartyBalance
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "party")
    private List<PartyBalance> partyBalances;

    // bi-directional many-to-one association to VoucherHeader
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "party")
    private List<VoucherHeader> voucherHeaders;
}