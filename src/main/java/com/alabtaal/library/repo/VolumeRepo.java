package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolumeRepo extends JpaRepository<VolumeEntity, UUID> {

  Page<VolumeEntity> findAll(Specification<VolumeEntity> spec, final Pageable pageable);

  List<VolumeEntity> findAllByBook(final BookEntity book);
}
