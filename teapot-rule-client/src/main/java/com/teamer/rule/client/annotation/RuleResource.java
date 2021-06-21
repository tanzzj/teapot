package com.teamer.rule.client.annotation;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2021/6/18
 */
@Data
@Accessors(chain = true)
public class RuleResource {

    private String nameSpace;
    private String group;
    private String className;
    private String fieldName;
    private String fieldDescription;
    private String fieldType;

}
