package com.kecoyo.turtlebase.common.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * 统一异常处理
 */
@Getter
public class BadRequestException extends RuntimeException {

    private Integer status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }

    public BadRequestException(int status, String message) {
        super(message);
        this.status = status;
    }
}
