package com.kecoyo.turtleopen.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "图标分类")
public class IconTypeVO {

    @Schema(description = "图标分类ID")
    private Integer id;

    @Schema(description = "图标分类名称")
    private String name;

}
