package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tanzj
 * @date 2022/8/2
 */
@Data
@Accessors(chain = true)
public class StrategyBO {

    private List<RuleBO> ruleList;
    private List<ActionBO> actionList;

}
