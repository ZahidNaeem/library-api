package com.alabtaal.library.specification;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.BookEntity_;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecification {

  static Specification<BookEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(BookEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasPublicationDate(final Date publicationDate) {
    return (root, query, criteriaBuilder) -> publicationDate != null ?
        criteriaBuilder.equal(root.get(BookEntity_.publicationDate), publicationDate) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasBookCondition(final String bookCondition) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(bookCondition) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(BookEntity_.bookCondition)), "%" + bookCondition.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> isPurchased(final Integer purchased) {
    return (root, query, criteriaBuilder) -> purchased != null ?
        criteriaBuilder.equal(root.get(BookEntity_.purchased), purchased) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(BookEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasAuthor(final UUID authorId) {
    return (root, query, criteriaBuilder) -> authorId != null ?
        criteriaBuilder.equal(root.get(BookEntity_.author), authorId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasSubject(final UUID subjectId) {
    return (root, query, criteriaBuilder) -> subjectId != null ?
        criteriaBuilder.equal(root.get(BookEntity_.subject), subjectId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasPublisher(final UUID publisherId) {
    return (root, query, criteriaBuilder) -> publisherId != null ?
        criteriaBuilder.equal(root.get(BookEntity_.publisher), publisherId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasResearcher(final UUID researcherId) {
    return (root, query, criteriaBuilder) -> researcherId != null ?
        criteriaBuilder.equal(root.get(BookEntity_.researcher), researcherId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<BookEntity> hasShelf(final UUID shelfId) {
    return (root, query, criteriaBuilder) -> shelfId != null ?
        criteriaBuilder.equal(root.get(BookEntity_.shelf), shelfId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
