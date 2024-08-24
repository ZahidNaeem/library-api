package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ShelfEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShelfRepo extends JpaRepository<ShelfEntity, UUID> {

  List<ShelfEntity> findAllByOrderByIdAsc();

  @Query(value = "select a from ShelfEntity a\n"
      + "where (:#{#shelfEntity.name} is null or a.name like concat('%',:#{#shelfEntity.name},'%'))\n"
      + "  and (:#{#shelfEntity.remarks} is null or a.remarks like concat('%',:#{#shelfEntity.remarks},'%'))")
  List<ShelfEntity> searchShelf(final ShelfEntity shelfEntity);
}