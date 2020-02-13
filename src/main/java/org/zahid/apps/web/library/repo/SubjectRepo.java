package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zahid.apps.web.library.entity.SubjectEntity;

import java.util.List;

public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findAllByOrderBySubjectIdAsc();

    @Query(value = "select a from SubjectEntity a\n"
        + "where (:#{#subjectEntity.subjectName} is null or a.subjectName like concat('%',:#{#subjectEntity.subjectName},'%'))\n"
        + "  and (:#{#subjectEntity.remarks} is null or a.remarks like concat('%',:#{#subjectEntity.remarks},'%'))")
    List<SubjectEntity> searchSubject(final SubjectEntity subjectEntity);
}
