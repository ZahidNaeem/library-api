package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_INVOICE_MAIN database table.
 */
@Entity
@Table(name = "XXIM_INVOICE_MAIN")
@JsonIdentityInfo(scope = InvoiceMain.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "invNum")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
        @NamedQuery(name = "InvoiceMain.findAll", query = "SELECT i FROM InvoiceMain i"),
        @NamedQuery(name = "InvoiceMain.findAllPO", query = "SELECT i FROM InvoiceMain i where i.invType = 'PO'"),
        @NamedQuery(name = "InvoiceMain.findAllPI", query = "SELECT i FROM InvoiceMain i where i.invType = 'PURCHASE'"),
        @NamedQuery(name = "InvoiceMain.findAllPRI", query = "SELECT i FROM InvoiceMain i where i.invType = 'PURCHASE RETURN'"),
        @NamedQuery(name = "InvoiceMain.findAllSO", query = "SELECT i FROM InvoiceMain i where i.invType = 'SO'"),
        @NamedQuery(name = "InvoiceMain.findAllSI", query = "SELECT i FROM InvoiceMain i where i.invType = 'SALE'"),
        @NamedQuery(name = "InvoiceMain.findAllSRI", query = "SELECT i FROM InvoiceMain i where i.invType = 'SALE RETURN'")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceMain extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*@SequenceGenerator(name = "XXIM_INVOICE_MAIN_INVNUM_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_INVOICE_MAIN_INVNUM_GENERATOR")*/
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "sequence_inv_main", strategy = "org.zahid.apps.web.pos.entity.idgenerator.InvoiceIdGenerator")
    @GeneratedValue(generator = "sequence_inv_main")
    @Column(name = "INV_NUM")
    private Long invNum;

    @Temporal(TemporalType.DATE)
    @Column(name = "INV_DATE")
    private Date invDate;

    @Column(name = "INV_TYPE")
    private String invType;

    @Column(name = "PAID_AMT")
    private BigDecimal paidAmt;

    private String reason;

    private String remarks;

    // bi-directional many-to-one association to InvoiceDtl
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoiceMain")
    private List<InvoiceDtl> invoiceDtls;

    // bi-directional many-to-one association to InvoiceMain
    @ManyToOne
    @JoinColumn(name = "REF_NUM")
    private InvoiceMain refInvoice;

    // bi-directional many-to-one association to InvoiceMain
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "refInvoice")
    private List<InvoiceMain> invoiceMains;

    // bi-directional many-to-one association to Party
    @ManyToOne
    @JoinColumn(name = "PARTY_CODE")
    private Party party;
}