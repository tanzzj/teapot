package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2022/8/2
 */
@Data
@Accessors(chain = true)
public class RuleBO {

    private FactorBO factor;
    private String operation;
    private Object value;
    private Boolean multiValue;

}
