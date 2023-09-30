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
public class Rule {

    private List<Condition> conditionList;
    private List<Action> action;

}
