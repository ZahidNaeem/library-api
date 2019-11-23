package org.zahid.apps.web.pos.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.zahid.apps.web.pos.entity.*;
import org.zahid.apps.web.pos.model.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrganizationMapper {

  @Autowired
  public UserMapper userMapper;

  @Mapping(target = "users", expression = "java(organization != null ? mapUsersToUserModels(organization.getUsers()) : null)")
  public abstract OrganizationModel fromOrganization(final Organization organization);

  @Mapping(target = "users", expression = "java(model != null ? mapUserModelsToUsers(model.getUsers()) : null)")
  public abstract Organization toOrganization(final OrganizationModel model);

  public List<UserModel> mapUsersToUserModels(final List<User> users) {
    return userMapper.mapUsersToUserModels(users);
  }

  public List<User> mapUserModelsToUsers(final List<UserModel> models) {
    return userMapper.mapUserModelsToUsers(models);
  }

  public List<OrganizationModel> mapOrganizationsToItemModels(final List<Organization> organizations) {
    if (CollectionUtils.isEmpty(organizations)) {
      return new ArrayList<>();
    }
    final List<OrganizationModel> models = new ArrayList<>();
    organizations.forEach(organization -> {
      models.add(this.fromOrganization(organization));
    });
    return models;
  }

  public List<Organization> mapOrganizationModelsToOrganizations(final List<OrganizationModel> models) {
    if (CollectionUtils.isEmpty(models)) {
      return new ArrayList<>();
    }
    final List<Organization> organizations = new ArrayList<>();
    models.forEach(model -> {
      organizations.add(this.toOrganization(model));
    });
    return organizations;
  }

}
