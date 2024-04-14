package com.kecoyo.turtlebase.common.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.kecoyo.turtlebase.common.utils.ThrowableUtil;
import com.kecoyo.turtlebase.common.web.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<Object>> handleException(Exception e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseResult<Object>> badRequestException(BadRequestException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(e.getStatus(), e.getMessage()));
    }

    // 数据库操作异常
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseResult<Object>> handleDataAccessException(DataAccessException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), e.getCause().getMessage()));
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String message = objectError.getDefaultMessage();
        if (objectError instanceof FieldError) {
            message = ((FieldError) objectError).getField() + ": " + message;
        }
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), message));
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseResult<Object>> handleHandlerMethodValidationException(
            HandlerMethodValidationException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 参数解析异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseResult<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * 用户名或密码错误
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseResult<Object>> badCredentialsException(BadCredentialsException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResponseEntity.ok(ResponseResult.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
