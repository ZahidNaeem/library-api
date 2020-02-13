package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.SubjectEntity;
import org.zahid.apps.web.library.repo.SubjectRepo;
import org.zahid.apps.web.library.service.SubjectService;

import java.util.List;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepo subjectRepo;

    @Override
    public List<SubjectEntity> findAll() {
        return subjectRepo.findAllByOrderBySubjectIdAsc();
    }

    @Override
    public List<SubjectEntity> searchSubject(SubjectEntity subjectEntity) {
        return subjectRepo.searchSubject(subjectEntity);
    }

    @Override
    public SubjectEntity findById(Long id) {
        return subjectRepo.findById(id)
                .orElse(new SubjectEntity());
    }

    @Override
    public boolean exists(Long id) {
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
    public void deleteById(Long id) {
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
}
