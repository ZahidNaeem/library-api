
package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookTransHeaderEntity.class)
public class BookTransHeaderEntity_ {

  public static volatile SingularAttribute<BookTransHeaderEntity, UUID> id;
  public static volatile SingularAttribute<BookTransHeaderEntity, String> transType;
  public static volatile SingularAttribute<BookTransHeaderEntity, Date> transDate;
  public static volatile SingularAttribute<BookTransHeaderEntity, UUID> reader;
  public static volatile SingularAttribute<BookTransHeaderEntity, String> remarks;
  public static volatile ListAttribute<BookTransHeaderEntity, UUID> bookTransLines;
}
