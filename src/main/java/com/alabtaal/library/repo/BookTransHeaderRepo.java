package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.ReaderEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransHeaderRepo extends JpaRepository<BookTransHeaderEntity, UUID> {

  Page<BookTransHeaderEntity> findAll(Specification<BookTransHeaderEntity> spec, final Pageable pageable);

  List<BookTransHeaderEntity> findAllByReader(final ReaderEntity reader);
}