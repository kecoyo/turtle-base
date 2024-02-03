package com.kecoyo.turtleopen.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    LOCKED(0, "锁定"),
    ACTIVED(1, "激活");

    private int id;
    private String name;

}
