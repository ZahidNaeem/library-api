package org.zahid.apps.web.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the ORGANIZATION database table.
 */
@Entity
@Table(name = "ORGANIZATION", schema = "library", uniqueConstraints = {
        @UniqueConstraint(name = "organization_name_uk", columnNames = {"organization_name"})
})
@JsonIdentityInfo(scope = Organization.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "organizationCode")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    @SequenceGenerator(name = "ORGANIZATION_ORGANIZATIONCODE_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_ORGANIZATIONCODE_GENERATOR")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORGANIZATION_CODE")
    private Long organizationCode;

    @Column(name = "ORGANIZATION_NAME")
    private String organizationName;

    @Column(name = "ORGANIZATION_OWNER")
    private String organizationOwner;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    private String address;

    private String email;

    private String phone;

    @Column(name = "CELL_NO")
    private String cellNo;

    private String fax;

    private String web;

    // bi-directional many-to-one association to InvoiceMain
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "organization")
    private List<User> users;
}