package com.teamer.teapot.rule.client;

import com.teamer.rule.client.annotation.RuleData;
import com.teamer.rule.client.annotation.RuleField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 * @date 2021/6/18
 */
@Data
@RuleData(description = "")
@Accessors(chain = true)
public class DatabaseTemp extends DatabaseTempSuper {

    @RuleField(description = "姓名")
    private String name;
    @RuleField(description = "电话")
    private String phone;
}
