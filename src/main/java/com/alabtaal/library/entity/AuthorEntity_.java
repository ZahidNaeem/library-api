package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuthorEntity.class)
public class AuthorEntity_ {

  public static volatile SingularAttribute<AuthorEntity, UUID> id;
  public static volatile SingularAttribute<AuthorEntity, String> name;
  public static volatile SingularAttribute<AuthorEntity, String> remarks;
  public static volatile ListAttribute<AuthorEntity, UUID> books;
}
