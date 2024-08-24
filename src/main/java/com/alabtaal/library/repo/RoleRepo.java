package com.alabtaal.library.repo;

import com.alabtaal.library.entity.RoleEntity;
import com.alabtaal.library.enumeration.RoleName;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, UUID> {

  Optional<RoleEntity> findByName(RoleName roleName);
}
