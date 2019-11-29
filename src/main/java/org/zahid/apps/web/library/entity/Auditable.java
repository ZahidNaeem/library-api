package org.zahid.apps.web.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zahid.apps.web.library.utils.Miscellaneous;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditable<U> {

    @CreatedBy
    @Column(name = "CREATED_BY")
    protected U createdBy;

    @Column(name = "CREATE_ORGANIZATION")
    protected U createOrganization;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "CREATION_DATE")
    protected Date creationDate;

    @LastModifiedBy
    @Column(name = "LAST_UPDATED_BY")
    protected U lastUpdatedBy;

    @Column(name = "LAST_UPDATE_ORGANIZATION")
    protected U lastUpdateOrganization;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "LAST_UPDATE_DATE")
    protected Date lastUpdateDate;

    @PrePersist
    public void persistCurrentOrganization() {
        final Optional<Organization> currentOrganization = Miscellaneous.getCurrentOrganization();

        if (currentOrganization.isPresent()) {
            final U organizationCode = (U) currentOrganization.get().getOrganizationCode();
            setCreateOrganization(organizationCode);
            setLastUpdateOrganization(organizationCode);
        }
    }

    @PreUpdate
    @PreRemove
    public void updateCurrentOrganization() {
        final Optional<Organization> currentOrganization = Miscellaneous.getCurrentOrganization();

        if (currentOrganization.isPresent()) {
            final U organizationCode = (U) currentOrganization.get().getOrganizationCode();
            setLastUpdateOrganization(organizationCode);
        }
    }
}
