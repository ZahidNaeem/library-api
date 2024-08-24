package com.alabtaal.library.service;

import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.repo.ResearcherRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearcherServiceImpl implements ResearcherService {

  private static final Logger LOG = LoggerFactory.getLogger(ResearcherServiceImpl.class);

  private final ResearcherRepo researcherRepo;

  @Override
  public List<ResearcherEntity> findAll() {
    return researcherRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<ResearcherEntity> searchResearcher(ResearcherEntity researcherEntity) {
    return researcherRepo.searchResearcher(researcherEntity);
  }

  @Override
  public ResearcherEntity findById(UUID id) {
    return researcherRepo.findById(id)
        .orElse(new ResearcherEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return researcherRepo.existsById(id);
  }

  @Override
  public ResearcherEntity save(ResearcherEntity researcher) {
    return researcherRepo.saveAndFlush(researcher);
  }

  @Override
  public List<ResearcherEntity> save(Set<ResearcherEntity> researchers) {
    return researcherRepo.saveAll(researchers);
  }

  @Override
  public void delete(ResearcherEntity researcher) {
    researcherRepo.delete(researcher);
  }

  @Override
  public void delete(Set<ResearcherEntity> researchers) {
    researcherRepo.deleteAll(researchers);
  }

  @Override
  public void deleteById(UUID id) {
    researcherRepo.deleteById(id);
  }

  @Override
  public void deleteAll() {
    researcherRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() {
    researcherRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<ResearcherEntity> researchers) {
    researcherRepo.deleteInBatch(researchers);
  }
}
