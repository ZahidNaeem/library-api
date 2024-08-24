package com.alabtaal.library.service;

import com.alabtaal.library.entity.ShelfEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ShelfService {

  List<ShelfEntity> findAll();

  List<ShelfEntity> searchShelf(final ShelfEntity shelfEntity);

  ShelfEntity findById(final UUID id);

  boolean exists(UUID id);

  ShelfEntity save(ShelfEntity shelf);

  List<ShelfEntity> save(Set<ShelfEntity> shelves);

  void delete(ShelfEntity shelf);

  void delete(Set<ShelfEntity> shelves);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<ShelfEntity> shelves);
}
