package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.BookEntity;

import java.util.List;
import java.util.Set;

public interface BookService {

    List<BookEntity> findAll();

    BookEntity findById(final Long id);

    boolean exists(Long id);

    BookEntity save(BookEntity book);

    List<BookEntity> save(Set<BookEntity> books);

    void delete(BookEntity book);

    void delete(Set<BookEntity> books);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<BookEntity> books);
}
