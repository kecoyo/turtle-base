package com.kecoyo.turtleopen.domain.dto;

import java.util.Set;

import com.alibaba.fastjson.JSONArray;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "用户管理的请求参数")
public class UserDTO {

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空", groups = {Update.class})
    private Integer id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空", groups = {Login.class, Update.class})
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空", groups = {Login.class, Update.class})
    private String password;

    // 分组校验
    public interface Login {
    }

    public interface Add {
    }

    public interface Update {
    }

    public interface Delete {
    }

    public interface Sort {
    }

}
