package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.model.UserModel;
import org.zahid.apps.web.pos.service.OrganizationService;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    public OrganizationService organizationService;

    @Mapping(target = "organization", expression = "java(user != null && user.getOrganization() != null ? user.getOrganization().getOrganizationCode() : null)")
    public abstract UserModel fromUser(final User user);

    @Mapping(target = "organization", expression = "java(model != null && model.getOrganization() != null ? organizationService.findById(model.getOrganization()) : null)")
    public abstract User toUser(final UserModel model);

    public List<UserModel> mapUsersToUserModels(final List<User> Users) {
        if (CollectionUtils.isEmpty(Users)) {
            return new ArrayList<>();
        }
        final List<UserModel> models = new ArrayList<>();
        Users.forEach(User -> {
            models.add(this.fromUser(User));
        });
        return models;
    }

    public List<User> mapUserModelsToUsers(final List<UserModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        final List<User> Users = new ArrayList<>();
        models.forEach(model -> {
            Users.add(this.toUser(model));
        });
        return Users;
    }
}
