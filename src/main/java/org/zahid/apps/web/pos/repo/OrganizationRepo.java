package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.Organization;
import org.zahid.apps.web.pos.entity.Role;
import org.zahid.apps.web.pos.enumeration.RoleName;

import java.util.Optional;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationName(final String organizationName);
}
