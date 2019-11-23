package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.ItemStock;

import java.util.List;
import java.util.Set;

public interface ItemStockService {

    Long generateID();

    boolean exists(Long id);

    List<ItemStock> findAll();

    List<ItemStock> getCurrentItemStockList();

    ItemStock findById(Long id);

    List<ItemStock> findAllByItem(Long itemCode);

    ItemStock save(ItemStock itemStock);

    List<ItemStock> save(Set<ItemStock> itemStocks);

    void delete(ItemStock itemStock);

    void delete(Set<ItemStock> itemStocks);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<ItemStock> itemStocks);

}
