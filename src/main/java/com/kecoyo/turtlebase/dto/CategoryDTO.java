package com.kecoyo.turtlebase.dto;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "账号分类的请求参数")
public class CategoryDTO {

    @Schema(description = "分类ID", example = "1")
    @NotNull(message = "分类ID不能为空", groups = { Update.class, Delete.class })
    private Integer id;

    @Schema(description = "分类名称", example = "网易邮箱")
    @NotBlank(message = "分类名称不能为空", groups = { Add.class, Update.class })
    @Length(min = 2, max = 10, message = "分类名称长度必须在2-10之间")
    private String name;

    @Schema(description = "分类图标", example = "")
    @NotBlank(message = "分类图标不能为空", groups = { Add.class, Update.class })
    private String icon;

    @Schema(description = "分类ID集合", example = "1")
    @NotNull(message = "分类ID集合不能为空", groups = { Sort.class })
    @Size(min = 1, message = "分类ID集合不能为空", groups = { Sort.class })
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

    public interface Sort {
    }

}
