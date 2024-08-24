package com.alabtaal.library.specification;

import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.entity.VolumeEntity_;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface VolumeSpecification {

  static Specification<VolumeEntity> hasRowKey(final String rowKey) {
    return (root, query, criteriaBuilder) -> rowKey != null ?
        criteriaBuilder.equal(root.get(VolumeEntity_.rowKey), rowKey) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<VolumeEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(VolumeEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<VolumeEntity> hasRemarks(final String remarks) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(remarks) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(VolumeEntity_.remarks)), "%" + remarks.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<VolumeEntity> hasBook(final UUID bookId) {
    return (root, query, criteriaBuilder) -> bookId != null ?
        criteriaBuilder.equal(root.get(VolumeEntity_.book), bookId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<VolumeEntity> hasRack(final UUID rackId) {
    return (root, query, criteriaBuilder) -> rackId != null ?
        criteriaBuilder.equal(root.get(VolumeEntity_.rack), rackId) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
