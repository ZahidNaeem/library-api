package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ResearcherEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherRepo extends JpaRepository<ResearcherEntity, UUID> {

  Page<ResearcherEntity> findAll(Specification<ResearcherEntity> spec, final Pageable pageable);
}
