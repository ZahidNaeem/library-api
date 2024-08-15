package com.alabtaal.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;

import java.util.List;

public interface BookTransHeaderRepo extends JpaRepository<BookTransHeaderEntity, Long> {
    List<BookTransHeaderEntity> findAllByOrderByHeaderIdAsc();

    List<BookTransHeaderEntity> findAllByReaderOrderByHeaderIdAsc(final ReaderEntity reader);

    List<BookTransHeaderEntity> findAllByTransTypeOrderByHeaderIdAsc(final String transType);
}