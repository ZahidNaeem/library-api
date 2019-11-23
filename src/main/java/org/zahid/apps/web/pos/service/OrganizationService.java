package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Organization;

public interface OrganizationService {

    Organization findById(final Long id);

    Organization findByOrganizationName(final String organizationName);
}
