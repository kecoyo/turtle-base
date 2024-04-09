package com.kecoyo.turtlebase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "区域")
@TableName("t_area")
public class Area implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    @Schema(description = "区域名称")
    private String name;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "级别")
    private Integer level;

}
