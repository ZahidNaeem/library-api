package com.alabtaal.library.repo;

import com.alabtaal.library.entity.ResearcherEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResearcherRepo extends JpaRepository<ResearcherEntity, UUID> {

  List<ResearcherEntity> findAllByOrderByIdAsc();

  @Query(value = "select a from ResearcherEntity a\n"
      + "where (:#{#researcherEntity.name} is null or a.name like concat('%',:#{#researcherEntity.name},'%'))\n"
      + "  and (:#{#researcherEntity.remarks} is null or a.remarks like concat('%',:#{#researcherEntity.remarks},'%'))")
  List<ResearcherEntity> searchResearcher(final ResearcherEntity researcherEntity);
}
