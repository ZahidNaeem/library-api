package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.ReaderEntity;

import java.util.List;

public interface ReaderRepo extends JpaRepository<ReaderEntity, Long> {
    List<ReaderEntity> findAllByOrderByReaderIdAsc();
}
