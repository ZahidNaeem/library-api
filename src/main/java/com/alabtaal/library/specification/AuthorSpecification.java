package com.alabtaal.library.specification;

import com.alabtaal.library.entity.AuthorEntity;
import com.alabtaal.library.entity.AuthorEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface AuthorSpecification {

  static Specification<AuthorEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(AuthorEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<AuthorEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(AuthorEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<AuthorEntity> hasBook(final UUID book) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(book, root.get(AuthorEntity_.books));
  }
}
