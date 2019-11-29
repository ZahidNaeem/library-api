package org.zahid.apps.web.library.service;

import org.zahid.apps.web.library.entity.Organization;

public interface OrganizationService {

    Organization findById(final Long id);

    Organization findByOrganizationName(final String organizationName);
}
