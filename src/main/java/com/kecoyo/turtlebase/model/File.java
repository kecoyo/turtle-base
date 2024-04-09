package com.kecoyo.turtlebase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "图标")
@TableName("butler_icon")
public class File implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "图标分类ID")
    private Integer iconTypeId;

    @Schema(description = "图标URL")
    private String url;

}
