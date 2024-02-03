package com.kecoyo.turtlebase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kecoyo.turtlebase.common.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "图标类型")
@TableName("butler_icon_type")
public class Log extends BaseEntity {

    @Schema(description = "分类名称")
    @TableField("name")
    private String name;

    @Schema(description = "分类描述")
    @TableField("remark")
    private String remark;

}
