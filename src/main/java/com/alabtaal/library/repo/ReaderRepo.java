package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ReaderEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepo extends JpaRepository<ReaderEntity, UUID> {

  Page<ReaderEntity> findAll(Specification<ReaderEntity> spec, final Pageable pageable);
}
