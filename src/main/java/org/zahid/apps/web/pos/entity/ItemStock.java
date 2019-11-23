package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the XXIM_ITEM_STOCK database table.
 */
@Entity
@Table(name = "XXIM_ITEM_STOCK")
@JsonIdentityInfo(scope = ItemStock.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemStockId")
//@JsonIdentityInfo(scope = ItemStock.class, generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@NamedQueries({
    @NamedQuery(name = "ItemStock.findAll", query = "SELECT i FROM ItemStock i"),
    @NamedQuery(name = "ItemStock.findAllByItem", query = "SELECT i FROM ItemStock i where i.item.itemCode = ?1"),
    @NamedQuery(name = "ItemStock.generateID", query = "SELECT coalesce(max(itemStockId), 0) + 1 FROM ItemStock i")
})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemStock extends Auditable<Long> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
//    @SequenceGenerator(name = "XXIM_ITEM_STOCK_ITEMSTOCKID_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_ITEM_STOCK_ITEMSTOCKID_GENERATOR")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ITEM_STOCK_ID")
  private Long itemStockId;

  @Temporal(TemporalType.DATE)
  @Column(name = "ITEM_STOCK_DATE")
  private Date itemStockDate;

  private BigDecimal qnty;

  private String remarks;

  // bi-directional many-to-one association to Item
  @ManyToOne
//  @NotNull
  @JoinColumn(name = "ITEM_CODE")
//    @JsonIdentityReference
  private Item item;
}