package com.augustoocc.demo.globant.domain.constants;

public enum ErrorCode {
    USER_NOT_FOUND("User not found", 1001),
    ROLE_NOT_FOUND("Role not found", 1002),
    USER_ALREADY_EXISTS("User already exists", 1003),
    INVALID_PASSWORD("Invalid password", 1004),
    EMAIL_REQUIRED("Email is required", 1006),
    PASSWORD_REQUIRED("Password is required", 1007),
    INVALID_EMAIL_FORMAT("Invalid email format", 1008),
    INVALID_NAME("Invalid name", 1009),
    INVALID_LAST_NAME("Invalid last name", 1010),
    EMAIL_PASSWORD_REQUIRED("Email and password are required", 1011),
    DECRIPTION_ERROR("Error on decrypting", 1012);

    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
