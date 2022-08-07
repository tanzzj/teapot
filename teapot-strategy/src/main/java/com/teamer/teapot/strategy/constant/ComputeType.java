package com.teamer.teapot.strategy.constant;

/**
 * @author tanzj
 * @date 2022/8/8
 */
public enum ComputeType {

    /**
     * 等于
     */
    EQUAL("==", "等于"),

    NOT_EQUAL("!=", "不等于"),

    LESS_THAN("<", "小于"),

    LESS_THAN_OR_EQUAL_TO("<=", "小于等于"),

    GREATER_THAN(">", "大于"),

    GREATER_THAN_OR_EQUAL_TO(">=", "大于等于"),

    IN("in", "大于等于"),

    NOT_IN("not_in", "大于等于"),

    TIME_AFTER("time_after", "时间之后"),

    TIME_BEFORE("time_before", "时间之前");

    String desc;
    String operation;

    ComputeType(String operation, String desc) {
        this.desc = desc;
        this.operation = operation;
    }


}
