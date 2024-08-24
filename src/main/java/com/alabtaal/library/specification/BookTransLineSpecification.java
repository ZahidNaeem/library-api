package com.alabtaal.library.specification;

import com.alabtaal.library.entity.BookTransLineEntity;
import com.alabtaal.library.entity.BookTransLineEntity_;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface BookTransLineSpecification {

  static Specification<BookTransLineEntity> hasRowKey(final String rowKey) {
    return (root, query, criteriaBuilder) -> rowKey != null ?
        criteriaBuilder.equal(root.get(BookTransLineEntity_.rowKey), rowKey) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransLineEntity> hasBook(final UUID bookId) {
    return (root, query, criteriaBuilder) -> bookId != null ?
        criteriaBuilder.equal(root.get(BookTransLineEntity_.book), bookId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransLineEntity> hasVolume(final UUID volumeId) {
    return (root, query, criteriaBuilder) -> volumeId != null ?
        criteriaBuilder.equal(root.get(BookTransLineEntity_.volume), volumeId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookTransLineEntity> hasBookTransHeader(final UUID bookTransHeaderId) {
    return (root, query, criteriaBuilder) -> bookTransHeaderId != null ?
        criteriaBuilder.equal(root.get(BookTransLineEntity_.bookTransHeader), bookTransHeaderId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
