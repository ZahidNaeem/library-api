package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.mapper.qualifier.Qualifier;
import com.alabtaal.library.payload.request.SignupRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = Qualifier.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

  @Mapping(target = "password", qualifiedByName = "password")
  @Mapping(target = "roles", qualifiedByName = "roles")
  UserEntity toEntity(final SignupRequest request);
}
