package org.zahid.apps.web.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.library.entity.Role;
import org.zahid.apps.web.library.enumeration.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName roleName);
}
