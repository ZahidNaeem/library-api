package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PublisherEntity.class)
public class PublisherEntity_ {

  public static volatile SingularAttribute<PublisherEntity, UUID> id;
  public static volatile SingularAttribute<PublisherEntity, String> name;
  public static volatile SingularAttribute<PublisherEntity, String> remarks;
  public static volatile ListAttribute<PublisherEntity, UUID> books;
}
