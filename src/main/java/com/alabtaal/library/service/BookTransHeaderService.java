package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;

import java.util.List;
import java.util.Set;

public interface BookTransHeaderService {

    List<BookTransHeaderEntity> findAll();

    List<BookTransHeaderEntity> findAllByTransType(final String transType);

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
