package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.ResearcherEntity;

import java.util.List;

public interface ResearcherRepo extends JpaRepository<ResearcherEntity, Long> {
    List<ResearcherEntity> findAllByOrderByResearcherIdAsc();
}
