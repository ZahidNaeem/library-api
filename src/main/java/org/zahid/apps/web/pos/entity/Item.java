package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_ITEMS database table.
 */
@Entity
@Table(name = "XXIM_ITEMS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ITEM_DESC"})
})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Item.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemCode")
//@JsonIdentityInfo(scope = Item.class, generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.generateID", query = "SELECT coalesce(max(itemCode), 0) + 1 FROM Item i"),
    @NamedQuery(name = "Item.getCategories", query = "SELECT distinct i.itemCategory FROM Item i where i.itemCategory is not null"),
    @NamedQuery(name = "Item.getItemDesc", query = "SELECT i.itemDesc FROM Item i where i.itemCode = :itemCode")
})

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends Auditable<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
//    @SequenceGenerator(name = "XXIM_ITEMS_ITEMCODE_GENERATOR", sequenceName = "XXIM_ITEMS_ITEMCODE_GENERATOR", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_ITEMS_ITEMCODE_GENERATOR")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ITEM_CODE")
  private Long itemCode;

  @Temporal(TemporalType.DATE)
  @Column(name = "EFFECTIVE_END_DATE")
  private Date effectiveEndDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "EFFECTIVE_START_DATE")
  private Date effectiveStartDate;

  @Column(name = "ITEM_BARCODE")
  private String itemBarcode;

  @Column(name = "ITEM_CATEGORY")
  private String itemCategory;

  @Column(name = "ITEM_DESC", unique = true)
  private String itemDesc;

  @Column(name = "ITEM_UOM")
  private String itemUom;

  @Column(name = "MAX_STOCK")
  private BigDecimal maxStock;

  @Column(name = "MIN_STOCK")
  private BigDecimal minStock;

  @Column(name = "PURCHASE_PRICE")
  private BigDecimal purchasePrice;

  @Column(name = "SALE_PRICE")
  private BigDecimal salePrice;

  // bi-directional many-to-one association to InvoiceDtl
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
  private List<InvoiceDtl> invoiceDtls;

  // bi-directional many-to-one association to ItemStock
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
//    @JsonIdentityReference
  private List<ItemStock> itemStocks;
}