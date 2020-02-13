package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.ShelfEntity;

import java.util.List;
import java.util.Set;

public interface ShelfService {

    List<ShelfEntity> findAll();

    List<ShelfEntity> searchShelf(final ShelfEntity shelfEntity);

    ShelfEntity findById(final Long id);

    boolean exists(Long id);

    ShelfEntity save(ShelfEntity shelf);

    List<ShelfEntity> save(Set<ShelfEntity> shelves);

    void delete(ShelfEntity shelf);

    void delete(Set<ShelfEntity> shelves);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<ShelfEntity> shelves);
}
