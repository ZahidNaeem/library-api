package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.ReaderEntity;

import java.util.List;
import java.util.Set;

public interface BookTransHeaderService {

    List<BookTransHeaderEntity> findAll();

    List<BookTransHeaderEntity> findAllByReader(final ReaderEntity reader);

    BookTransHeaderEntity findById(final Long id);

    boolean exists(Long id);

    BookTransHeaderEntity save(BookTransHeaderEntity bookTransHeader);

    List<BookTransHeaderEntity> save(Set<BookTransHeaderEntity> shelves);

    void delete(BookTransHeaderEntity bookTransHeader);

    void delete(Set<BookTransHeaderEntity> shelves);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<BookTransHeaderEntity> shelves);
}