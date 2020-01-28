package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.ReaderEntity;

import java.util.List;
import java.util.Set;

public interface ReaderService {

    List<ReaderEntity> findAll();

    ReaderEntity findById(final Long id);

    boolean exists(Long id);

    ReaderEntity save(ReaderEntity reader);

    List<ReaderEntity> save(Set<ReaderEntity> readers);

    void delete(ReaderEntity reader);

    void delete(Set<ReaderEntity> readers);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<ReaderEntity> readers);
}