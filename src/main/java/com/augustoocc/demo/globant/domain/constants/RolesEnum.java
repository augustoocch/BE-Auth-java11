package com.augustoocc.demo.globant.domain.constants;

import lombok.Getter;

@Getter
public enum RolesEnum {
    ROLE_ADMIN("ADMIN", 1),
    ROLE_USER("USER", 2),
    ROLE_MODERATOR("MODERATOR", 3);

    private final String role;
    private final int id;

    RolesEnum(String role, int id) {
        this.role = role;
        this.id = id;
    }
}
