package com.alabtaal.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.ShelfEntity;

import java.util.List;

public interface RackRepo extends JpaRepository<RackEntity, Long> {
    List<RackEntity> findAllByOrderByRackIdAsc();

    List<RackEntity> findAllByShelfOrderByRackIdAsc(final ShelfEntity shelf);
}
