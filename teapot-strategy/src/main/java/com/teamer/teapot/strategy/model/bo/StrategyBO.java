package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author tanzj
 * @date 2022/8/2
 */
@Data
@Accessors(chain = true)
public class StrategyBO {

    private Long id;
    private List<RuleBO> ruleList;
    private Map<String, Object> extend;

}
