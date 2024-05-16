package com.augustoocc.demo.globant.domain.constants;

public enum RolesEnum {
    ROLE_USER("ROLE_USER", 1),
    ROLE_ADMIN("ROLE_ADMIN", 2),
    ROLE_MODERATOR("ROLE_MODERATOR", 3);

    private final String role;
    private final int id;

    RolesEnum(String role, int id) {
        this.role = role;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
}
