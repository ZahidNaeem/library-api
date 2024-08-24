package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ResearcherEntity.class)
public class ResearcherEntity_ {

  public static volatile SingularAttribute<ResearcherEntity, UUID> id;
  public static volatile SingularAttribute<ResearcherEntity, String> name;
  public static volatile SingularAttribute<ResearcherEntity, String> remarks;
  public static volatile ListAttribute<ResearcherEntity, UUID> books;
}
