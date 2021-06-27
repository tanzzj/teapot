package com.teamer.teapot.rule.client;

import com.teamer.rule.client.annotation.RuleField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2021/6/18
 */
@Data
@Accessors(chain = true)
public class DatabaseTempSuper {

    @RuleField
    private String mobile;
}
