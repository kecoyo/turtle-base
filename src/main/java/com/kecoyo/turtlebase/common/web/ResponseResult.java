package com.kecoyo.turtlebase.common.web;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装 ResponseResult响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {

    /**
     * 消息状态码
     */
    private int code;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息数据
     */
    private T data;

    public ResponseResult(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.msg = httpStatus.getReasonPhrase();
    }

    public ResponseResult(int code, T body) {
        this.code = code;
        this.data = body;
        this.msg = HttpStatus.valueOf(code).getReasonPhrase();
    }

    public static ResponseResult<Object> success() {
        return new ResponseResult<>(0, "请求成功", null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(0, "请求成功", data);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<>(0, msg, data);
    }

    public static <T> ResponseResult<T> success(int code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

    public static ResponseResult<Object> fail() {
        return new ResponseResult<>(400, "请求失败", null);
    }

    public static ResponseResult<Object> fail(String msg) {
        return new ResponseResult<>(400, msg, null);
    }

    public static <T> ResponseResult<T> fail(String msg, T data) {
        return new ResponseResult<>(400, msg, data);
    }

    public static ResponseResult<Object> fail(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    public static <T> ResponseResult<T> fail(int code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

}
