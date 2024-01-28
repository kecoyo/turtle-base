package com.kecoyo.turtleopen.common.utils;

import java.io.Serializable;

import com.kecoyo.turtleopen.common.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装httpClient响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResult implements Serializable {

    private Integer status;
    private String body;
    private String message;

    public HttpClientResult(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public HttpClientResult(int status, String body) {
        this.status = status;
        this.body = body;
        this.message = HttpStatus.valueOf(status).getReasonPhrase();
    }

    public JSONObject getJSONObject() {
        if (StringUtils.isNotBlank(this.body)) {
            return JSON.parseObject(this.body);
        }
        return null;
    }

    public JSONArray getJSONArray() {
        if (StringUtils.isNotBlank(this.body)) {
            return JSON.parseArray(this.body);
        }
        return null;
    }

    public JSONObject parseApiData() {
        if (this.getStatus() != 200) {
            throw new BadRequestException("服务接口调用失败");
        }
        JSONObject body = this.getJSONObject();
        if (body == null) {
            throw new BadRequestException("服务接口返回数据格式错误");
        }
        if (body.getInteger("code") != 0) {
            throw new BadRequestException(body.getString("msg"));
        }
        JSONObject data = body.getJSONObject("data");
        return data;
    }

    @Override
    public String toString() {
        return "HttpClientResult [status=" + status + ", body=" + body + "]";
    }
}
