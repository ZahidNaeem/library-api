package org.zahid.apps.web.pos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.pos.entity.Auditable;
import org.zahid.apps.web.pos.entity.Organization;
import org.zahid.apps.web.pos.entity.Role;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends Auditable<Long> {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Long organization;
}
