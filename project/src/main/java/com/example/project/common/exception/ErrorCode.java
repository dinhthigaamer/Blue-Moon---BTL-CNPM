package com.example.project.common.exception;

public enum ErrorCode {

    // GLOBAL
    GLOBAL_ERROR,
    VALIDATION_ERROR,
    METHOD_NOT_ALLOWED,
    UNAUTHORIZED,

    // AUTH
    AUTH_INVALID_CREDENTIAL,
    AUTH_USER_NOT_FOUND,
    AUTH_USERNAME_EXISTED,
    AUTH_PHONE_EXISTED,
    AUTH_CCCD_EXISTED,

    // Những module khác thêm sau...
    ;
}
