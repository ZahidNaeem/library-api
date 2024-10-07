package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ShelfEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepo extends JpaRepository<ShelfEntity, UUID> {

  Page<ShelfEntity> findAll(Specification<ShelfEntity> spec, final Pageable pageable);
}