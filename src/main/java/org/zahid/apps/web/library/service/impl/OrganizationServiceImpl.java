package org.zahid.apps.web.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.Organization;
import org.zahid.apps.web.library.repo.OrganizationRepo;
import org.zahid.apps.web.library.service.OrganizationService;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepo organizationRepo;

    @Override
    public Organization findById(final Long id) {
        return organizationRepo.findById(id)
                .orElse(new Organization());
    }

    @Override
    public Organization findByOrganizationName(final String organizationName) {
        return organizationRepo.findByOrganizationName(organizationName)
                .orElse(null);
    }
}
