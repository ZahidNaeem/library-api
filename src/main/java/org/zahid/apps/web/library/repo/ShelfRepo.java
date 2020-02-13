package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zahid.apps.web.library.entity.ShelfEntity;

import java.util.List;

public interface ShelfRepo extends JpaRepository<ShelfEntity, Long> {
    List<ShelfEntity> findAllByOrderByShelfIdAsc();

    @Query(value = "select a from ShelfEntity a\n"
        + "where (:#{#shelfEntity.shelfName} is null or a.shelfName like concat('%',:#{#shelfEntity.shelfName},'%'))\n"
        + "  and (:#{#shelfEntity.remarks} is null or a.remarks like concat('%',:#{#shelfEntity.remarks},'%'))")
    List<ShelfEntity> searchShelf(final ShelfEntity shelfEntity);
}