package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BookTransLineEntity.class)
public class BookTransLineEntity_ {

  public static volatile SingularAttribute<BookTransLineEntity, UUID> id;
  public static volatile SingularAttribute<BookTransLineEntity, String> rowKey;
  public static volatile SingularAttribute<BookTransLineEntity, UUID> book;
  public static volatile SingularAttribute<BookTransLineEntity, UUID> volume;
  public static volatile SingularAttribute<BookTransLineEntity, UUID> bookTransHeader;
}
