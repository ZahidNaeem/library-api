package com.alabtaal.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.alabtaal.library.entity.ResearcherEntity;

import java.util.List;

public interface ResearcherRepo extends JpaRepository<ResearcherEntity, Long> {
    List<ResearcherEntity> findAllByOrderByResearcherIdAsc();

    @Query(value = "select a from ResearcherEntity a\n"
        + "where (:#{#researcherEntity.researcherName} is null or a.researcherName like concat('%',:#{#researcherEntity.researcherName},'%'))\n"
        + "  and (:#{#researcherEntity.remarks} is null or a.remarks like concat('%',:#{#researcherEntity.remarks},'%'))")
    List<ResearcherEntity> searchResearcher(final ResearcherEntity researcherEntity);
}
