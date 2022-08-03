package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author tanzj
 * @date 2022/8/2
 */
@Data
@Accessors(chain = true)
public class ConditionBO {

    private Long id;
    private FactorBO factor;
    private OperationType operation;
    private Object value;
    private Boolean multiValue;
    private Map<String, Object> extend;

    public enum OperationType {
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

        NOT_IN("not in", "大于等于"),


        ;

        String desc;
        String operation;

        OperationType(String operation, String desc) {
            this.desc = desc;
            this.operation = operation;
        }
    }

}
