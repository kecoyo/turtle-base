package com.kecoyo.turtleopen.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassJoinMode {

    FORBID(1, "禁止加入"),
    FREE(2, "自由加入"),
    APPROVE(4, "批准加入");

    private final Integer id;
    private final String name;

}
