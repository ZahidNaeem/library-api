package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.SubjectEntity;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    List<SubjectEntity> findAll();

    List<SubjectEntity> searchSubject(final SubjectEntity subjectEntity);

    SubjectEntity findById(final Long id);

    boolean exists(Long id);

    SubjectEntity save(SubjectEntity subject);

    List<SubjectEntity> save(Set<SubjectEntity> subjects);

    void delete(SubjectEntity subject);

    void delete(Set<SubjectEntity> subjects);

    void deleteById(Long id);

    void deleteAll();

    void deleteAllInBatch();

    void deleteInBatch(Set<SubjectEntity> subjects);
}
