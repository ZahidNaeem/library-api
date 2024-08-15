package com.alabtaal.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.alabtaal.library.entity.ReaderEntity;

import java.util.List;

public interface ReaderRepo extends JpaRepository<ReaderEntity, Long> {
    List<ReaderEntity> findAllByOrderByReaderIdAsc();

    @Query(value = "select a from ReaderEntity a\n"
        + "where (:#{#readerEntity.readerName} is null or a.readerName like concat('%',:#{#readerEntity.readerName},'%'))\n"
        + "  and (:#{#readerEntity.remarks} is null or a.remarks like concat('%',:#{#readerEntity.remarks},'%'))")
    List<ReaderEntity> searchReader(final ReaderEntity readerEntity);
}
