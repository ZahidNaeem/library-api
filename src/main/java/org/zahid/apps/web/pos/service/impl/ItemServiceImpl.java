package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.exception.ItemNotFoundException;
import org.zahid.apps.web.pos.repo.ItemRepo;
import org.zahid.apps.web.pos.service.ItemService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

  private ItemRepo itemRepo;
  private final Logger LOG = LogManager.getLogger(ItemServiceImpl.class.getName());

  public ItemServiceImpl() {

  }

  @Autowired
  public ItemServiceImpl(ItemRepo itemRepo) {
    this.itemRepo = itemRepo;
  }

  private Sort orderBy(String column) {
    return Sort.by(Sort.Direction.ASC, column);
  }

  @Override
  public Long generateID() {
    return itemRepo.generateID();
  }

  @Override
  public boolean exists(Long id) {
    return itemRepo.existsById(id);
  }

  @Override
  public List<Item> findAll() {
    return itemRepo.findAll(orderBy("itemCode"));
  }

  @Override
  public Item findById(Long id) {
        return Optional.ofNullable(itemRepo.findById(id))
                .map(item -> item.get())
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));

   /* final Optional<Item> item = itemRepo.findById(id);
    if (item.isPresent()) {
      return item.get();
    }
    throw new ItemNotFoundException("Item with id " + id + " not found");*/
  }

  @Override
  public Item save(Item item) throws DataIntegrityViolationException {
    return itemRepo.save(item);
  }

  @Override
  public List<Item> save(Set<Item> items) throws DataIntegrityViolationException {

    List<Item> returnItems = itemRepo.saveAll(items);
    return returnItems;
  }

  @Override
  public void delete(Item item) throws DataIntegrityViolationException {
    itemRepo.delete(item);
  }

  @Override
  public void delete(Set<Item> items) throws DataIntegrityViolationException {
    itemRepo.deleteAll(items);
  }

  @Override
  public void deleteById(Long id) throws DataIntegrityViolationException {
    itemRepo.deleteById(id);
  }

  @Override
  public void deleteAll() throws DataIntegrityViolationException {
    itemRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() throws DataIntegrityViolationException {
    itemRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<Item> items) throws DataIntegrityViolationException {
    itemRepo.deleteInBatch(items);
  }

  @Override
  public Set<String> getItemCategories() {
    Set<String> cats = new HashSet<>();
    cats.addAll(itemRepo.getCategories());
//        for (Item item : itemRepo.findAll()) {
//            cats.add(item.getItemCategory());
//        }
    return cats;
  }

  @Override
  public Set<String> getItemUOM() {
    Set<String> uoms = new HashSet<>();
    for (Item item : itemRepo.findAll()) {
      uoms.add(item.getItemUom());
    }
    return uoms;
  }

  @Override
  public String getItemDesc(Long itemCode) {
    return itemRepo.getItemDesc(itemCode);
  }
}
