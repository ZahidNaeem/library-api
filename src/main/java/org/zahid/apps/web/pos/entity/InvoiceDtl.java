package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the XXIM_INVOICE_DTL database table.
 */
@Entity
@Table(name = "XXIM_INVOICE_DTL")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@JsonIdentityInfo(scope = InvoiceDtl.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "invDtlNum")
@NamedQueries({
    @NamedQuery(name = "InvoiceDtl.findAll", query = "SELECT i FROM InvoiceDtl i"),
    @NamedQuery(name = "InvoiceDtl.findByInvoice", query = "SELECT i FROM InvoiceDtl i where i.invoiceMain.invNum = ?1")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDtl extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*@SequenceGenerator(name = "XXIM_INVOICE_DTL_INVDTLNUM_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_INVOICE_DTL_INVDTLNUM_GENERATOR")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INV_DTL_NUM")
    private Long invDtlNum;

    @Column(name = "ITEM_PRICE")
    private BigDecimal itemPrice;

    private BigDecimal qnty;

    // bi-directional many-to-one association to InvoiceMain
    @ManyToOne
    @JoinColumn(name = "INV_NUM")
    private InvoiceMain invoiceMain;

    // bi-directional many-to-one association to Item
    @ManyToOne
    @JoinColumn(name = "ITEM_CODE")
    private Item item;}