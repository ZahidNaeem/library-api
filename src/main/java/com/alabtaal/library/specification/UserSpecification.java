package com.alabtaal.library.specification;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.entity.UserEntity_;
import com.alabtaal.library.enumeration.ActivationStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpecification {

  static Specification<UserEntity> hasName(final String name) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(name) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(UserEntity_.name)), "%" + name.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<UserEntity> hasUsername(final String username) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(username) ?
        criteriaBuilder.equal(root.get(UserEntity_.username), username) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<UserEntity> hasEmail(final String email) {
    return (root, query, criteriaBuilder) -> StringUtils.isNotBlank(email) ?
        criteriaBuilder.like(criteriaBuilder.upper(root.get(UserEntity_.email)), "%" + email.toUpperCase() + "%") :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }

  static Specification<UserEntity> hasActivationStatus(final ActivationStatus activationStatus) {
    return (root, query, criteriaBuilder) -> activationStatus != null ?
        criteriaBuilder.equal(root.get(UserEntity_.activationStatus), activationStatus) :
        criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
  }
}
