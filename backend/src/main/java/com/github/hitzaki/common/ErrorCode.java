package com.github.hitzaki.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SYSTEM_ERROR(500, "System Error"),
    PARAM_ERROR(400, "Parameter Error"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    
    USER_NOT_FOUND(1001, "User not found"),
    PASSWORD_ERROR(1002, "Password error"),
    RATE_LIMIT(1003, "Too many requests, please try again later");

    private final int code;
    private final String msg;
}

