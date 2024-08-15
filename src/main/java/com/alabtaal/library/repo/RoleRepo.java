package com.alabtaal.library.repo;

import com.alabtaal.library.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alabtaal.library.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(RoleName roleName);
}
