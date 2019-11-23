package org.zahid.apps.web.pos.model;

import lombok.*;
import org.zahid.apps.web.pos.entity.Auditable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel  extends Auditable<Long> implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long itemCode;
  private Date effectiveEndDate;
  private Date effectiveStartDate;
  private String itemBarcode;
  private String itemCategory;
  private String itemDesc;
  private String itemUom;
  private BigDecimal maxStock;
  private BigDecimal minStock;
  private BigDecimal purchasePrice;
  private BigDecimal salePrice;
  private List<InvoiceDtlModel> invoiceDtls;
  private List<ItemStockModel> itemStocks;
}