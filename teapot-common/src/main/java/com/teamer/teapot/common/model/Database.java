package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Data
@Accessors(chain = true)
public class Database {
    private Integer id;
    private String databaseId;
    private String databaseName;
    private String databaseConnection;
    private String username;
    private String password;
    private String databaseType;
}
