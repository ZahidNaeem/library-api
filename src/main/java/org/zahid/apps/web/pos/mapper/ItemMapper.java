package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.InvoiceDtl;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.entity.ItemStock;
import org.zahid.apps.web.pos.model.InvoiceDtlModel;
import org.zahid.apps.web.pos.model.ItemModel;
import org.zahid.apps.web.pos.model.ItemStockModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

  @Autowired
  public InvoiceDtlMapper invoiceDtlMapper;

  @Autowired
  public ItemStockMapper itemStockMapper;

  @Mapping(target = "invoiceDtls", expression = "java(item != null ? mapInvoiceDtlsToInvoiceDtlModels(item.getInvoiceDtls()) : null)")
  @Mapping(target = "itemStocks", expression = "java(item != null ? mapItemStocksToItemStockModels(item.getItemStocks()) : null)")
  public abstract ItemModel fromItem(final Item item);

  @Mapping(target = "invoiceDtls", expression = "java(model != null ? mapInvoiceDtlModelsToInvoiceDtls(model.getInvoiceDtls()) : null)")
  @Mapping(target = "itemStocks", expression = "java(model != null ? mapItemStockModelsToItemStocks(model.getItemStocks()) : null)")
  public abstract Item toItem(final ItemModel model);

  public List<InvoiceDtlModel> mapInvoiceDtlsToInvoiceDtlModels(final List<InvoiceDtl> invoiceDtls) {
    return invoiceDtlMapper.mapInvoiceDtlsToInvoiceDtlModels(invoiceDtls);
  }

  public List<InvoiceDtl> mapInvoiceDtlModelsToInvoiceDtls(final List<InvoiceDtlModel> models) {
    return invoiceDtlMapper.mapInvoiceDtlModelsToInvoiceDtls(models);
  }

  public List<ItemStockModel> mapItemStocksToItemStockModels(final List<ItemStock> stocks) {
    return itemStockMapper.mapItemStocksToItemStockModels(stocks);
  }

  public List<ItemStock> mapItemStockModelsToItemStocks(final List<ItemStockModel> models) {
    return itemStockMapper.mapItemStockModelsToItemStocks(models);
  }

  public List<ItemModel> mapItemsToItemModels(final List<Item> items) {
    if (CollectionUtils.isEmpty(items)) {
      return new ArrayList<>();
    }
    final List<ItemModel> models = new ArrayList<>();
    items.forEach(item -> {
      models.add(this.fromItem(item));
    });
    return models;
  }

  public List<Item> mapItemModelsToItems(final List<ItemModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<Item> items = new ArrayList<>();
    models.forEach(model -> {
      items.add(this.toItem(model));
    });
    return items;
  }

}
