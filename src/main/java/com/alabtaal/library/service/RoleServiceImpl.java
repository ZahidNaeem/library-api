package com.alabtaal.library.service;

import com.alabtaal.library.entity.RoleEntity;
import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.repo.RoleRepo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepo roleRepo;

  @Override
  public RoleEntity findById(final UUID id) {
    final RoleEntity roleEntity = roleRepo.findById(id)
        .orElse(null);
    return roleEntity;
  }

  @Override
  public RoleEntity findByName(final RoleName roleName) {
    final RoleEntity roleEntity = roleRepo.findByName(roleName)
        .orElse(null);
    return roleEntity;
  }

  @Override
  public RoleEntity findByNameThrowError(final RoleName roleName) throws ResourceNotFoundException {
    final RoleEntity roleEntity = roleRepo.findByName(roleName)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName.name()));
    return roleEntity;
  }
}
