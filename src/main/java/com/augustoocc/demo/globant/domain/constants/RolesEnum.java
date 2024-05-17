package com.augustoocc.demo.globant.domain.constants;

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

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
}
