package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.SubjectEntity;

import java.util.List;

public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
    List<SubjectEntity> findAllByOrderBySubjectIdAsc();
}
