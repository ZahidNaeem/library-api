package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.payload.response.SearchBookResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookService {

  List<BookEntity> findAll();

  List<BookEntity> searchBook(final BookEntity bookEntity);

  List<SearchBookResponse> searchByCriteria(
      final UUID author,
      final UUID subject,
      final UUID publisher,
      final UUID researcher);

  BookEntity findById(final UUID id);

  boolean exists(UUID id);

  BookEntity save(BookEntity book);

  List<BookEntity> save(Set<BookEntity> books);

  void delete(BookEntity book);

  void delete(Set<BookEntity> books);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<BookEntity> books);
}
