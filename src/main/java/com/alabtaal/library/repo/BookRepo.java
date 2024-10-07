package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, UUID> {

  Page<BookEntity> findAll(Specification<BookEntity> spec, final Pageable pageable);
}
