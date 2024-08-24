package com.alabtaal.library.specification;

import com.alabtaal.library.entity.ResearcherEntity;
import com.alabtaal.library.entity.ResearcherEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface ResearcherSpecification {

  static Specification<ResearcherEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(ResearcherEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<ResearcherEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(ResearcherEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<ResearcherEntity> hasBook(final UUID book) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(book, root.get(ResearcherEntity_.books));
  }
}
