package com.kecoyo.turtleopen.common.security.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class RoleDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
