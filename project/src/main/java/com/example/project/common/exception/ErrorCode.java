package com.example.project.common.exception;

public enum ErrorCode {

    //COMMON
    BAD_REQUEST,

    // GLOBAL
    GLOBAL_ERROR,
    VALIDATION_ERROR,
    METHOD_NOT_ALLOWED,
    UNAUTHORIZED,
    NOT_FOUND,
    NULL_ERROR,

    // AUTH
    AUTH_INVALID_CREDENTIAL,
    AUTH_USER_NOT_FOUND,
    AUTH_USERNAME_EXISTED,
    AUTH_PHONE_EXISTED,
    AUTH_CCCD_EXISTED,
    AUTH_INVALID_PASSWORD;

}
