package com.alabtaal.library.specification;

import com.alabtaal.library.entity.RackEntity;
import com.alabtaal.library.entity.RackEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface RackSpecification {

  static Specification<RackEntity> hasRowKey(final String rowKey) {
    return (root, query, criteriaBuilder) -> rowKey != null ?
        criteriaBuilder.equal(root.get(RackEntity_.rowKey), rowKey) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<RackEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(RackEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<RackEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(RackEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<RackEntity> hasShelf(final UUID shelfId) {
    return (root, query, criteriaBuilder) -> shelfId != null ?
        criteriaBuilder.equal(root.get(RackEntity_.shelf), shelfId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
