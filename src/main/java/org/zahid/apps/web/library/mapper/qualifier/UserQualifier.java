package org.zahid.apps.web.library.mapper.qualifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.library.entity.Organization;
import org.zahid.apps.web.library.service.OrganizationService;

@Component
@Data
@RequiredArgsConstructor
public class UserQualifier {

  private final OrganizationService organizationService;

  @Named("organization")
  public Organization organization(final Long organization) {
    return organization != null ? organizationService.findById(organization) : null;
  }
}
