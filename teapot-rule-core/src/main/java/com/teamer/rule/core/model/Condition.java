package com.teamer.rule.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tanzj
 * @date 2023/9/30
 */
@Data
@Accessors(chain = true)
public class Condition {

    private Factor factor;
    private RuleOperation operation;
    private String value;
    private List<String> values;
}
