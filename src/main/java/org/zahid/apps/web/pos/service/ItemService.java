package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    Long generateID();

    boolean exists(Long id);

    Item findById(Long id);

    List<Item> findAll();

    Set<String> getItemCategories();

    Set<String> getItemUOM();

    String getItemDesc(Long itemCode);

    Item save(Item item);

    List<Item> save(Set<Item> items);

    void delete(Item item);

    void delete(Set<Item> items);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<Item> items);
}
