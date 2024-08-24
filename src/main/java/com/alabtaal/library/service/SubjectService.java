package com.alabtaal.library.service;

import com.alabtaal.library.entity.SubjectEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SubjectService {

  List<SubjectEntity> findAll();

  List<SubjectEntity> searchSubject(final SubjectEntity subjectEntity);

  SubjectEntity findById(final UUID id);

  boolean exists(UUID id);

  SubjectEntity save(SubjectEntity subject);

  List<SubjectEntity> save(Set<SubjectEntity> subjects);

  void delete(SubjectEntity subject);

  void delete(Set<SubjectEntity> subjects);

  void deleteById(UUID id);

  void deleteAll();

  void deleteAllInBatch();

  void deleteInBatch(Set<SubjectEntity> subjects);

  String getSubjectHierarchy(final UUID id);
}
