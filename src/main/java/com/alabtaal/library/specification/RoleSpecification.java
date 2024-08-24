package com.alabtaal.library.specification;

import com.alabtaal.library.entity.RoleEntity;
import com.alabtaal.library.entity.RoleEntity_;
import com.alabtaal.library.enumeration.RoleName;
import org.springframework.data.jpa.domain.Specification;

public interface RoleSpecification {

  static Specification<RoleEntity> hasName(final RoleName name) {
    return (root, query, criteriaBuilder) -> name != null ?
        criteriaBuilder.equal(root.get(RoleEntity_.name), name) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
