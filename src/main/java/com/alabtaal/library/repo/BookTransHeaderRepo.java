package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTransHeaderRepo extends JpaRepository<BookTransHeaderEntity, UUID> {

  List<BookTransHeaderEntity> findAllByOrderByIdAsc();

  List<BookTransHeaderEntity> findAllByReaderOrderByIdAsc(final ReaderEntity reader);

  List<BookTransHeaderEntity> findAllByTransTypeOrderByIdAsc(final String transType);
}