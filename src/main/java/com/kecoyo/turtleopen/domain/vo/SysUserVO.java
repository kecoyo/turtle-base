package com.kecoyo.turtleopen.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.kecoyo.turtleopen.domain.entity.SysUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账号信息")
public class SysUserVO extends SysUser {

    @Schema(description = "第一个属性")
    private JSONObject firstProperty;

    @Schema(description = "图片数量")
    private Integer pictureNum;

}
