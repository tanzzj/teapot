package com.teamer.teapot.common.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanzj
 */
@Data
@Accessors(chain = true)
public abstract class AbstractDatabaseExecuteVO {
    private String sqlType;
}
