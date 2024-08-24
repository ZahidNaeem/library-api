package com.alabtaal.library.specification;

import com.alabtaal.library.entity.BookTransHeaderEntity;
import com.alabtaal.library.entity.BookTransHeaderEntity_;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface BookTransHeaderSpecification {

  static Specification<BookTransHeaderEntity> hasTransType(final String transType) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(transType) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(BookTransHeaderEntity_.transType)), "%" + transType.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransHeaderEntity> hasTransDate(final Date transDate) {
    return (root, query, criteriaBuilder) -> transDate != null ?
        criteriaBuilder.equal(root.get(BookTransHeaderEntity_.transDate), transDate) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransHeaderEntity> hasReader(final UUID readerId) {
    return (root, query, criteriaBuilder) -> readerId != null ?
        criteriaBuilder.equal(root.get(BookTransHeaderEntity_.reader), readerId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransHeaderEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(BookTransHeaderEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
