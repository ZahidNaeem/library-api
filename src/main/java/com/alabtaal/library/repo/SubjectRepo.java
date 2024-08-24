package com.alabtaal.library.repo;

import com.alabtaal.library.entity.SubjectEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepo extends JpaRepository<SubjectEntity, UUID> {

  List<SubjectEntity> findAllByOrderByIdAsc();

  @Query(value = "select a from SubjectEntity a\n"
      + "where (:#{#subjectEntity.name} is null or a.name like concat('%',:#{#subjectEntity.name},'%'))\n"
      + "  and (:#{#subjectEntity.remarks} is null or a.remarks like concat('%',:#{#subjectEntity.remarks},'%'))")
  List<SubjectEntity> searchSubject(final SubjectEntity subjectEntity);

  @Query(
      nativeQuery = true,
      value = "select library.get_subject_hierarchy(:id) from dual")
  String getSubjectHierarchy(@Param("id") final UUID id);
}
