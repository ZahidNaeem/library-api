package org.zahid.apps.web.library.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.library.entity.User;
import org.zahid.apps.web.library.model.UserModel;
import org.zahid.apps.web.library.service.OrganizationService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected OrganizationService organizationService;

    @Mapping(target = "organization", expression = "java(user != null && user.getOrganization() != null ? user.getOrganization().getOrganizationCode() : null)")
    public abstract UserModel toModel(final User user);

    @Mapping(target = "organization", expression = "java(model != null && model.getOrganization() != null ? organizationService.findById(model.getOrganization()) : null)")
    public abstract User toEntity(final UserModel model);

    protected List<UserModel> toModels(final List<User> Users) {
        if (CollectionUtils.isEmpty(Users)) {
            return new ArrayList<>();
        }
        final List<UserModel> models = new ArrayList<>();
        Users.forEach(User -> {
            models.add(this.toModel(User));
        });
        return models;
    }

    protected List<User> toEntities(final List<UserModel> models) {
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
