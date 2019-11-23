package org.zahid.apps.web.pos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.pos.entity.Auditable;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModel extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long organizationCode;
    private String organizationName;
    private String organizationOwner;
    private String contactPerson;
    private String address;
    private String email;
    private String phone;
    private String cellNo;
    private String fax;
    private String web;
    private List<UserModel> users;
}
