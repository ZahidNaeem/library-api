package com.alabtaal.library.service;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;

import java.util.List;
import java.util.Set;

public interface RackService {

    List<RackEntity> findAll();

    List<RackEntity> findAllByShelf(final ShelfEntity shelf);

    RackEntity findById(final Long id);

    boolean exists(Long id);

    RackEntity save(RackEntity rack);

    List<RackEntity> save(Set<RackEntity> racks);

    void delete(RackEntity rack);

    void delete(Set<RackEntity> racks);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<RackEntity> racks);
}
