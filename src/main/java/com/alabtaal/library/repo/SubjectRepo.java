package com.alabtaal.library.repo;

import com.alabtaal.library.entity.SubjectEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepo extends JpaRepository<SubjectEntity, UUID> {

  Page<SubjectEntity> findAll(Specification<SubjectEntity> spec, final Pageable pageable);

  @Query(
      nativeQuery = true,
      value = "select library.get_subject_hierarchy(:id) from dual")
  String getSubjectHierarchy(@Param("id") final UUID id);
}
