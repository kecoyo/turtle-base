package com.kecoyo.turtlebase.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteStatus {
    UNDELETED(0, "未删除"),
    DELETED(1, "已删除");

    private int id;
    private String name;

}
