package org.zahid.apps.web.library.mapper;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.zahid.apps.web.library.entity.User;
import org.zahid.apps.web.library.mapper.qualifier.UserQualifier;
import org.zahid.apps.web.library.model.UserModel;

@Mapper(
    componentModel = "spring",
    uses = {UserQualifier.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    @Mapping(target = "organization", expression = "java(user != null && user.getOrganization() != null ? user.getOrganization().getOrganizationCode() : null)")
    UserModel toModel(final User user);

    @Mapping(target = "organization", qualifiedByName = "organization")
    User toEntity(final UserModel model);

    default List<UserModel> toModels(final List<User> Users) {
        if (CollectionUtils.isEmpty(Users)) {
            return new ArrayList<>();
        }
        final List<UserModel> models = new ArrayList<>();
        Users.forEach(User -> {
            models.add(this.toModel(User));
        });
        return models;
    }

    default List<User> toEntities(final List<UserModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<User> Users = new ArrayList<>();
        models.forEach(model -> {
            Users.add(this.toEntity(model));
        });
        return Users;
    }
}
