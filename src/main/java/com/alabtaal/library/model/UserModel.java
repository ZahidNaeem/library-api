package com.alabtaal.library.model;

import com.alabtaal.library.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alabtaal.library.entity.Auditable;

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
    private Set<RoleEntity> roles = new HashSet<>();
    private Long organization;
}
