package com.github.hitzaki.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.github.hitzaki.common.BusinessException;
import com.github.hitzaki.common.ErrorCode;
import com.github.hitzaki.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("Business Exception: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Result<?> handleNotLoginException(NotLoginException e) {
        return Result.error(ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler({NotRoleException.class, NotPermissionException.class})
    public Result<?> handleNotPermissionException(Exception e) {
        return Result.error(ErrorCode.FORBIDDEN);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<?> handleValidationException(Exception e) {
        log.warn("Validation Exception: {}", e.getMessage());
        return Result.error(ErrorCode.PARAM_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("System Error", e);
        return Result.error(ErrorCode.SYSTEM_ERROR);
    }
}

