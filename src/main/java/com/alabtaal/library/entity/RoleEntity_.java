package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.RoleName;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RoleEntity.class)
public class RoleEntity_ {

  public static volatile SingularAttribute<RoleEntity, UUID> id;
  public static volatile SingularAttribute<RoleEntity, RoleName> name;
}
