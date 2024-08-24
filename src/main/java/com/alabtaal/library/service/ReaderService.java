package com.alabtaal.library.service;

import com.alabtaal.library.entity.ReaderEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ReaderService {

  List<ReaderEntity> findAll();

  List<ReaderEntity> searchReader(final ReaderEntity readerEntity);

  ReaderEntity findById(final UUID id);

  boolean exists(UUID id);

  ReaderEntity save(ReaderEntity reader);

  List<ReaderEntity> save(Set<ReaderEntity> readers);

  void delete(ReaderEntity reader);

  void delete(Set<ReaderEntity> readers);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<ReaderEntity> readers);
}
