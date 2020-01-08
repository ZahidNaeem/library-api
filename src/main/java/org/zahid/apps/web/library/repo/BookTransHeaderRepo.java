package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.BookTransHeaderEntity;
import org.zahid.apps.web.library.entity.ReaderEntity;

import java.util.List;

public interface BookTransHeaderRepo extends JpaRepository<BookTransHeaderEntity, Long> {
    List<BookTransHeaderEntity> findAllByOrderByBookTransHeaderIdAsc();

    List<BookTransHeaderEntity> findAllByReaderOrderByBookTransHeaderIdAsc(final ReaderEntity reader);
}