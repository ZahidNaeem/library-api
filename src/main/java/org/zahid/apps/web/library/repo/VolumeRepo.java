package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.VolumeEntity;
import org.zahid.apps.web.library.entity.BookEntity;

import java.util.List;

public interface VolumeRepo extends JpaRepository<VolumeEntity, Long> {
    List<VolumeEntity> findAllByOrderByVolumeIdAsc();

    List<VolumeEntity> findAllByBookOrderByVolumeIdAsc(final BookEntity book);
}
