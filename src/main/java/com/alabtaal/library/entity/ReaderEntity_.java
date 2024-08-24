package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReaderEntity.class)
public class ReaderEntity_ {

  public static volatile SingularAttribute<ReaderEntity, UUID> id;
  public static volatile SingularAttribute<ReaderEntity, String> name;
  public static volatile SingularAttribute<ReaderEntity, String> remarks;
  public static volatile ListAttribute<ReaderEntity, UUID> bookTransHeaders;
}
