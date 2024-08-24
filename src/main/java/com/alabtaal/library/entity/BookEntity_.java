package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookEntity.class)
public class BookEntity_ {

  public static volatile SingularAttribute<BookEntity, UUID> id;
  public static volatile SingularAttribute<BookEntity, String> name;
  public static volatile SingularAttribute<BookEntity, Date> publicationDate;
  public static volatile SingularAttribute<BookEntity, String> bookCondition;
  public static volatile SingularAttribute<BookEntity, Integer> purchased;
  public static volatile SingularAttribute<BookEntity, String> remarks;
  public static volatile ListAttribute<BookEntity, UUID> volumes;
  public static volatile ListAttribute<BookEntity, UUID> bookTransLines;
  public static volatile SingularAttribute<BookEntity, UUID> author;
  public static volatile SingularAttribute<BookEntity, UUID> subject;
  public static volatile SingularAttribute<BookEntity, UUID> publisher;
  public static volatile SingularAttribute<BookEntity, UUID> researcher;
  public static volatile SingularAttribute<BookEntity, UUID> shelf;
}
