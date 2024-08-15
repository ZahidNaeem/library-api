package com.alabtaal.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.exception.ChildRecordFoundException;
import com.alabtaal.library.repo.SubjectRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private static final Logger LOG = LogManager.getLogger(SubjectServiceImpl.class);

    private final SubjectRepo subjectRepo;

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
        try {
            subjectRepo.deleteById(id);
        } catch (Exception e) {
            final Exception ex = Miscellaneous.getNestedException(e);
            LOG.error("Exception in delete: {}", ex.getMessage());
            if(ex.getMessage().startsWith("ORA-02292")){
                throw new ChildRecordFoundException("You can't delete this subject. It is associated with other subjects as parent");
            }
        }
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
