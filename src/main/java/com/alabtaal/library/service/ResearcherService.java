package com.alabtaal.library.service;

import com.alabtaal.library.entity.ResearcherEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ResearcherService {

  List<ResearcherEntity> findAll();

  List<ResearcherEntity> searchResearcher(final ResearcherEntity researcherEntity);

  ResearcherEntity findById(final UUID id);

  boolean exists(UUID id);

  ResearcherEntity save(ResearcherEntity researcher);

  List<ResearcherEntity> save(Set<ResearcherEntity> researchers);

  void delete(ResearcherEntity researcher);

  void delete(Set<ResearcherEntity> researchers);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<ResearcherEntity> researchers);
}
