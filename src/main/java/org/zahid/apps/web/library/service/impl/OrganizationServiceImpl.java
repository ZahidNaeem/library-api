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
        return Optional.ofNullable(organizationRepo.findById(id))
                .map(organization -> organization.get())
                .orElseThrow(() -> new OrganizationNotFoundException("Item with id " + id + " not found"));
    }

    @Override
    public Organization findByOrganizationName(final String organizationName) {
        return Optional.ofNullable(organizationRepo.findByOrganizationName(organizationName))
                .map(organization -> organization.get())
                .orElseThrow(() -> new OrganizationNotFoundException("Item with name '" + organizationName + "' not found"));
    }
}
