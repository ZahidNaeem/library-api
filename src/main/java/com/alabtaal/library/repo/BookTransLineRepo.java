package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransLineEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransLineRepo extends JpaRepository<BookTransLineEntity, UUID> {

  Page<BookTransLineEntity> findAll(Specification<BookTransLineEntity> spec, final Pageable pageable);

  List<BookTransLineEntity> findAllByBookTransHeader(final BookTransHeaderEntity bookTransHeader);
}
