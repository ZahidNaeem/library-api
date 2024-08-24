package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookTransLineService {

  List<BookTransLineEntity> findAll();

  List<BookTransLineEntity> findAllByBookTransHeader(final BookTransHeaderEntity bookTransHeader);

  BookTransLineEntity findById(final UUID id);

  boolean exists(UUID id);

  BookTransLineEntity save(BookTransLineEntity bookTransLine);

  List<BookTransLineEntity> save(Set<BookTransLineEntity> bookTransLines);

  void delete(BookTransLineEntity bookTransLine);

  void delete(Set<BookTransLineEntity> bookTransLines);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<BookTransLineEntity> bookTransLines);
}
