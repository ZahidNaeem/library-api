package com.alabtaal.library.repo;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RackRepo extends JpaRepository<RackEntity, UUID> {

  List<RackEntity> findAllByOrderByIdAsc();

  List<RackEntity> findAllByShelfOrderByIdAsc(final ShelfEntity shelf);
}
