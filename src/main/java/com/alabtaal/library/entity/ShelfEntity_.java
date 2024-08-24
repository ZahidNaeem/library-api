package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShelfEntity.class)
public class ShelfEntity_ {

  public static volatile SingularAttribute<ShelfEntity, UUID> id;
  public static volatile SingularAttribute<ShelfEntity, String> name;
  public static volatile SingularAttribute<ShelfEntity, String> remarks;
  public static volatile ListAttribute<ShelfEntity, UUID> books;
  public static volatile ListAttribute<ShelfEntity, UUID> racks;
}
