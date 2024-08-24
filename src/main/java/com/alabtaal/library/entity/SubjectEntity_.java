package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SubjectEntity.class)
public class SubjectEntity_ {

  public static volatile SingularAttribute<SubjectEntity, UUID> id;
  public static volatile SingularAttribute<SubjectEntity, String> code;
  public static volatile SingularAttribute<SubjectEntity, String> name;
  public static volatile SingularAttribute<SubjectEntity, UUID> parentSubject;
  public static volatile SingularAttribute<SubjectEntity, String> remarks;
  public static volatile ListAttribute<SubjectEntity, UUID> books;
  public static volatile ListAttribute<SubjectEntity, UUID> subjects;
}
