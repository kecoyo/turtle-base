package com.kecoyo.turtleopen.common.base;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseEntity implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // @Schema(description = "创建人")
    // @TableField("create_by")
    // private String createBy;

    // @Schema(description = "更新人")
    // @TableField("update_by")
    // private String updateBy;

    @Schema(description = "创建时间")
    @TableField("create_at")
    private String createAt;

    @Schema(description = "更新时间")
    @TableField("update_at")
    private String updateAt;

    @Schema(description = "状态(0禁用,1启用)")
    @TableField("status")
    private Integer status;

    @Schema(description = "删除状态(0正常,1删除)")
    @TableField("deleted")
    private Integer deleted;

    // 有效数据查询条件
    public static final Map<String, Integer> ValidDataParams = Map.of("deleted", 0, "status", 1);

}
