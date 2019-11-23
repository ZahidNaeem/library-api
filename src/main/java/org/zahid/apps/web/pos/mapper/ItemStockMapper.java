package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.ItemStock;
import org.zahid.apps.web.pos.model.ItemStockModel;
import org.zahid.apps.web.pos.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ItemStockMapper {

    @Autowired
    public  ItemService itemService;

    @Mapping(target = "item", expression = "java(itemStock != null && itemStock.getItem() != null ? itemStock.getItem().getItemCode() : null)")
    public abstract ItemStockModel fromItemStock(final ItemStock itemStock);

    @Mapping(target = "item", expression = "java(model != null && model.getItem() != null ? itemService.findById(model.getItem()) : null)")
    public abstract ItemStock toItemStock(final ItemStockModel model);

    public List<ItemStockModel> mapItemStocksToItemStockModels(final List<ItemStock> ItemStocks) {
        if (CollectionUtils.isEmpty(ItemStocks)) {
            return new ArrayList<>();
        }
        final List<ItemStockModel> models = new ArrayList<>();
        ItemStocks.forEach(ItemStock -> {
            models.add(this.fromItemStock(ItemStock));
        });
        return models;
    }

    public List<ItemStock> mapItemStockModelsToItemStocks(final List<ItemStockModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<ItemStock> ItemStocks = new ArrayList<>();
        models.forEach(model -> {
            ItemStocks.add(this.toItemStock(model));
        });
        return ItemStocks;
    }

    // Below commented code was added to test how multiple custom mappings with same datatypes will be performed

  /*
  @Mapping(target = "item2", source = "model.item2", qualifiedByName = "diItem2")
  @Mapping(target = "item3", source = "model.item3", qualifiedByName = "diItem3")
  ItemStock toItemStock(final ItemStockModel model, final ItemService itemService);

  @Mapping(target = "item2", source = "itemStock.item2", qualifiedByName = "idItem2")
  @Mapping(target = "item3", source = "itemStock.item3", qualifiedByName = "idItem3")
  ItemStockModel fromItemStock(final ItemStock itemStock);

  @Named("diItem2")
  default Item mapItem2(Long value) {
    return null;
  }

  @Named("diItem3")
  default Item mapItem3(Long value) {
    return null;
  }

  @Named("idItem2")
  default Long mapItem2(Item value) {
    return null;
  }

  @Named("idItem3")
  default Long mapItem3(Item value) {
    return null;
  }
  */
}
