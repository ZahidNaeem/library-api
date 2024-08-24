package com.alabtaal.library.specification;

import com.alabtaal.library.entity.ReaderEntity;
import com.alabtaal.library.entity.ReaderEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface ReaderSpecification {

  static Specification<ReaderEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(ReaderEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<ReaderEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(ReaderEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
