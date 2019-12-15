package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.RackEntity;
import org.zahid.apps.web.library.entity.ShelfEntity;

import java.util.List;

public interface RackRepo extends JpaRepository<RackEntity, Long> {
    List<RackEntity> findAllByOrderByRackIdAsc();

    List<RackEntity> findAllByShelfOrderByRackIdAsc(final ShelfEntity shelf);
}
