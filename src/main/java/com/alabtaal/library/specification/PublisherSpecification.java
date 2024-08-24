package com.alabtaal.library.specification;

import com.alabtaal.library.entity.PublisherEntity;
import com.alabtaal.library.entity.PublisherEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface PublisherSpecification {

  static Specification<PublisherEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(PublisherEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<PublisherEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(PublisherEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<PublisherEntity> hasBook(final UUID book) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(book, root.get(PublisherEntity_.books));
  }
}
