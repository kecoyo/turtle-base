package com.kecoyo.turtleopen.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Data
@Schema(description = "账号管理的请求参数")
public class CategoryVO {
    @Schema(description = "地域ID", example = "0")
    private Integer areaId = 0;

    @Schema(description = "用户的ID、姓名、手机号", example = "")
    private String userKey;

    @Schema(description = "用户ID", example = "1")
    @NotNull(groups = { Update.class, UpdateStatus.class }, message = "用户ID不能为空")
    private Integer id;

    @Schema(description = "姓名", example = "张三")
    @NotBlank(groups = { Add.class, Update.class }, message = "姓名不能为空")
    @Length(min = 2, max = 10, message = "姓名长度必须在2-10之间")
    private String name;

    @Schema(description = "手机号", example = "13800138000")
    @NotBlank(groups = { Add.class, Update.class }, message = "手机号不能为空")
    private String phone;

    @Schema(description = "性别", example = "1")
    @NotNull(groups = { Add.class, Update.class }, message = "性别不能为空")
    @Range(min = 1, max = 2, message = "性别只能为1或2")
    private Integer gender;

    @Schema(description = "用户状态, 0-启用 1-停用", example = "")
    @NotNull(groups = { Add.class, Update.class, UpdateStatus.class }, message = "用户状态不能为空")
    @Range(min = 0, max = 1, message = "用户状态只能为0或1")
    private Integer status;

    @Schema(description = "角色,1-超级管理员，2-管理员，3-运维人员", example = "3")
    @NotNull(groups = { Add.class, Update.class }, message = "角色不能为空")
    @Range(min = 1, max = 3, message = "角色只能为1或2或3")
    private Integer role;

    @Schema(description = "区域范围, 多个地域ID以逗号分隔", example = "1,2,3")
    @NotBlank(groups = { Add.class, Update.class }, message = "区域范围不能为空")
    private String areaRange;

    @Schema(description = "用户ID集合", example = "1,2,3")
    @NotNull(groups = { Delete.class }, message = "用户ID集合不能为空")
    @Size(min = 1, message = "用户ID集合不能为空")
    private Set<Integer> ids;

    // 分组校验
    public interface Query {
    }

    public interface Add {
    }

    public interface Update {
    }

    public interface Delete {
    }

    public interface UpdateStatus {
    }

}
