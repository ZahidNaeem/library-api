package com.alabtaal.library.repo;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RackRepo extends JpaRepository<RackEntity, UUID> {

  Page<RackEntity> findAll(Specification<RackEntity> spec, final Pageable pageable);

  List<RackEntity> findAllByShelf(final ShelfEntity shelf);
}
