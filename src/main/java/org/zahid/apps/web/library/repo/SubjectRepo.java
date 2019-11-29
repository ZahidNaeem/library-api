package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.library.entity.SubjectEntity;

public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
}
