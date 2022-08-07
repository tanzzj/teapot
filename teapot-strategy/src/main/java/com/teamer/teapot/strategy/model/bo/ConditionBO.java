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

    }

}
