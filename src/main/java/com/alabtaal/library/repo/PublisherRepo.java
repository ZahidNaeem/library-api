package com.alabtaal.library.repo;

import com.alabtaal.library.entity.PublisherEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepo extends JpaRepository<PublisherEntity, UUID> {

  Page<PublisherEntity> findAll(Specification<PublisherEntity> spec, final Pageable pageable);
}
