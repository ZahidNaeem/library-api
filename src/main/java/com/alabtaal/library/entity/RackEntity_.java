package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RackEntity.class)
public class RackEntity_ {

  public static volatile SingularAttribute<RackEntity, UUID> id;
  public static volatile SingularAttribute<RackEntity, String> rowKey;
  public static volatile SingularAttribute<RackEntity, String> name;
  public static volatile SingularAttribute<RackEntity, String> remarks;
  public static volatile ListAttribute<RackEntity, UUID> volumes;
  public static volatile SingularAttribute<RackEntity, UUID> shelf;
}
