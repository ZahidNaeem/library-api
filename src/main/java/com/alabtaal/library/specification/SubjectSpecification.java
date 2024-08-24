package com.alabtaal.library.specification;

import com.alabtaal.library.entity.SubjectEntity;
import com.alabtaal.library.entity.SubjectEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface SubjectSpecification {

  static Specification<SubjectEntity> hasCode(final String code) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(code) ?
        criteriaBuilder.equal(root.get(SubjectEntity_.code), code) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<SubjectEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(SubjectEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<SubjectEntity> hasParentSubject(final UUID parentSubjectId) {
    return (root, query, criteriaBuilder) -> parentSubjectId != null ?
        criteriaBuilder.equal(root.get(SubjectEntity_.parentSubject), parentSubjectId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<SubjectEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(SubjectEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
