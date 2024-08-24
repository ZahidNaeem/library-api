package com.alabtaal.library.entity;

import com.alabtaal.library.enumeration.ActivationStatus;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public class UserEntity_ {

  public static volatile SingularAttribute<UserEntity, UUID> id;
  public static volatile SingularAttribute<UserEntity, String> name;
  public static volatile SingularAttribute<UserEntity, String> username;
  public static volatile SingularAttribute<UserEntity, String> email;
  public static volatile SingularAttribute<UserEntity, String> password;
  public static volatile SingularAttribute<UserEntity, ActivationStatus> activationStatus;
  public static volatile SetAttribute<UserEntity, UUID> roles;
}
