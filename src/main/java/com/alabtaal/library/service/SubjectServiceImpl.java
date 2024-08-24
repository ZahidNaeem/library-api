package com.alabtaal.library.service;

import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.repo.SubjectRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private static final Logger LOG = LoggerFactory.getLogger(SubjectServiceImpl.class);

  private final SubjectRepo subjectRepo;

  @Override
  public List<SubjectEntity> findAll() {
    return subjectRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<SubjectEntity> searchSubject(SubjectEntity subjectEntity) {
    return subjectRepo.searchSubject(subjectEntity);
  }

  @Override
  public SubjectEntity findById(UUID id) {
    return subjectRepo.findById(id)
        .orElse(new SubjectEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return subjectRepo.existsById(id);
  }

  @Override
  public SubjectEntity save(SubjectEntity subject) {
    return subjectRepo.saveAndFlush(subject);
  }

  @Override
  public List<SubjectEntity> save(Set<SubjectEntity> subjects) {
    return subjectRepo.saveAll(subjects);
  }

  @Override
  public void delete(SubjectEntity subject) {
    subjectRepo.delete(subject);
  }

  @Override
  public void delete(Set<SubjectEntity> subjects) {
    subjectRepo.deleteAll(subjects);
  }

  @Override
  public void deleteById(UUID id) {
    subjectRepo.deleteById(id);
  }

  @Override
  public void deleteAll() {
    subjectRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() {
    subjectRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<SubjectEntity> subjects) {
    subjectRepo.deleteInBatch(subjects);
  }

  @Override
  public String getSubjectHierarchy(UUID id) {
  return subjectRepo.getSubjectHierarchy(id);
  }
}
