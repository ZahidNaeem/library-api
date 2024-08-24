package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookTransHeaderService {

  List<BookTransHeaderEntity> findAll();

  List<BookTransHeaderEntity> findAllByTransType(final String transType);

  List<BookTransHeaderEntity> findAllByReader(final ReaderEntity reader);

  BookTransHeaderEntity findById(final UUID id);

  boolean exists(UUID id);

  BookTransHeaderEntity save(BookTransHeaderEntity bookTransHeader);

  List<BookTransHeaderEntity> save(Set<BookTransHeaderEntity> shelves);

  void delete(BookTransHeaderEntity bookTransHeader);

  void delete(Set<BookTransHeaderEntity> shelves);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<BookTransHeaderEntity> shelves);
}
