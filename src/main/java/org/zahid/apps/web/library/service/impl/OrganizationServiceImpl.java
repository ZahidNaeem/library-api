package org.zahid.apps.web.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.library.entity.Organization;
import org.zahid.apps.web.library.exception.OrganizationNotFoundException;
import org.zahid.apps.web.library.repo.OrganizationRepo;
import org.zahid.apps.web.library.service.OrganizationService;

import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

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
