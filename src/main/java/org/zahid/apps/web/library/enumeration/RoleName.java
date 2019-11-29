package org.zahid.apps.web.library.enumeration;

import org.zahid.apps.web.library.exception.RoleNameNotFoundException;

import java.util.Arrays;

public enum RoleName {
    ROLE_USER("user"),
    ROLE_PM("pm"),
    ROLE_ADMIN("admin");

    private String value;

    RoleName(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleName fromValue(final String value) {
        return Arrays.stream(RoleName.values())
                .filter(role -> value.equals(role.getValue())).findFirst().orElseThrow(
                        () -> new RoleNameNotFoundException("Role with value '" + value + "' not found"));
    }
}
