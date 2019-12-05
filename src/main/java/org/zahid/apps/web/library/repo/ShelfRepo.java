package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.ShelfEntity;

public interface ShelfRepo extends JpaRepository<ShelfEntity, Long> {
}