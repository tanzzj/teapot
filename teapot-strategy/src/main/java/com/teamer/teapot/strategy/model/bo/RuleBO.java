package com.teamer.teapot.strategy.model.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author tanzj
 * @date 2022/8/3
 */
@Data
@Accessors(chain = true)
public class RuleBO {

    private Long id;
    private List<ConditionBO> conditionList;
    private ConditionOperateEnum operate;
    private ActionBO action;
    private Map<String, String> ext;

    public enum ConditionOperateEnum {
        /**
         * and
         */
        AND,
        OR;
    }

}
