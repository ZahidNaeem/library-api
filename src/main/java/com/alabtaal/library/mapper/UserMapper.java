package com.alabtaal.library.mapper;

import com.alabtaal.library.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.alabtaal.library.mapper.qualifier.UserQualifier;
import com.alabtaal.library.model.UserModel;

@Mapper(
    componentModel = "spring",
    uses = {UserQualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    @Mapping(target = "organization", expression = "java(user != null && user.getOrganization() != null ? user.getOrganization().getOrganizationCode() : null)")
    UserModel toModel(final UserEntity user);

    @Mapping(target = "organization", qualifiedByName = "organization")
    UserEntity toEntity(final UserModel model);

    default List<UserModel> toModels(final List<UserEntity> Users) {
        if (CollectionUtils.isEmpty(Users)) {
            return new ArrayList<>();
        }
        final List<UserModel> models = new ArrayList<>();
        Users.forEach(User -> {
            models.add(this.toModel(User));
        });
        return models;
    }

    default List<UserEntity> toEntities(final List<UserModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<UserEntity> Users = new ArrayList<>();
        models.forEach(model -> {
            Users.add(this.toEntity(model));
        });
        return Users;
    }
}
