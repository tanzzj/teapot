package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : tanzj
 * @date : 2020/8/10.
 */
@Data
@Accessors(chain = true)
public class SQLParams {

    private String sql;
    private String databaseId;
    private String tableName;


}
