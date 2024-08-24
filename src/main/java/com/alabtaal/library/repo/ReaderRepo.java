package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ReaderEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReaderRepo extends JpaRepository<ReaderEntity, UUID> {

  List<ReaderEntity> findAllByOrderByIdAsc();

  @Query(value = "select a from ReaderEntity a\n"
      + "where (:#{#readerEntity.name} is null or a.name like concat('%',:#{#readerEntity.name},'%'))\n"
      + "  and (:#{#readerEntity.remarks} is null or a.remarks like concat('%',:#{#readerEntity.remarks},'%'))")
  List<ReaderEntity> searchReader(final ReaderEntity readerEntity);
}
