package com.kecoyo.turtleopen.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kecoyo.turtleopen.common.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "图标")
@TableName("butler_icon")
public class SysFile extends BaseEntity {

    @Schema(description = "图标分类ID")
    private Integer iconTypeId;

    @Schema(description = "图标URL")
    private String url;

}
