package com.augustoocc.demo.globant.domain.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User not found", 1001),
    ROLE_NOT_FOUND("Role not found", 1002),
    USER_ALREADY_EXISTS("User already exists", 1003),
    INVALID_PASSWORD("Invalid password", 1004),
    EMAIL_REQUIRED("Email is required", 1005),
    PASSWORD_REQUIRED("Password is required", 1006),
    INVALID_EMAIL_FORMAT("Invalid email format", 1007),
    EMAIL_PASSWORD_REQUIRED("Email and password are required", 1008),
    ENCRYPTION_ERROR("Error on encrypting", 1009),
    DECRYPTION_ERROR("Error on decrypting", 1010);

    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
