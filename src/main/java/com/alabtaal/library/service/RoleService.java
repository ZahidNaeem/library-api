package com.alabtaal.library.service;

import com.alabtaal.library.entity.RoleEntity;
import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.exception.ResourceNotFoundException;
import java.util.UUID;

public interface RoleService {

  RoleEntity findById(final UUID id);

  RoleEntity findByName(final RoleName roleName);

  RoleEntity findByNameThrowError(final RoleName roleName) throws ResourceNotFoundException;
}
