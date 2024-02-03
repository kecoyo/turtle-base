package com.kecoyo.turtlebase.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "图标管理的请求参数")
public class IconTypeDTO {

    @Schema(description = "图标分类ID", example = "0")
    private Integer iconTypeId = 0;

}
