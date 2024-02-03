package com.kecoyo.turtlebase.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.kecoyo.turtlebase.domain.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账号信息")
public class UserVo extends User {

    @Schema(description = "第一个属性")
    private JSONObject firstProperty;

    @Schema(description = "图片数量")
    private Integer pictureNum;

}
