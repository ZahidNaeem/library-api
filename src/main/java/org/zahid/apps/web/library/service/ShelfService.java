package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.ShelfEntity;

import java.util.List;
import java.util.Set;

public interface ShelfService {

    List<ShelfEntity> findAll();

    ShelfEntity findById(final Long id);

    boolean exists(Long id);

    ShelfEntity save(ShelfEntity shelf);

    List<ShelfEntity> save(Set<ShelfEntity> shelfs);

    void delete(ShelfEntity shelf);

    void delete(Set<ShelfEntity> shelfs);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<ShelfEntity> shelfs);
}
