package com.alabtaal.library.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VolumeEntity.class)
public class VolumeEntity_ {

  public static volatile SingularAttribute<VolumeEntity, UUID> id;
  public static volatile SingularAttribute<VolumeEntity, String> rowKey;
  public static volatile SingularAttribute<VolumeEntity, String> name;
  public static volatile SingularAttribute<VolumeEntity, String> remarks;
  public static volatile SingularAttribute<VolumeEntity, UUID> book;
  public static volatile SingularAttribute<VolumeEntity, UUID> rack;
  public static volatile ListAttribute<VolumeEntity, UUID> bookTransLines;
}
