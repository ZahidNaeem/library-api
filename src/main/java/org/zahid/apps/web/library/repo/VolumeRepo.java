package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.entity.BookEntity;

import java.util.List;

public interface VolumeRepo extends JpaRepository<VolumeEntity, Long> {
    List<VolumeEntity> findAllByOrderByVolumeIdAsc();

    List<VolumeEntity> findAllByBookOrderByVolumeIdAsc(final BookEntity book);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM Volume v where v.book_id =:id")
    List<VolumeEntity> findAllByBookId(final Long id);
}
