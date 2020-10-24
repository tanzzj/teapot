package com.teamer.teapot.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Data
@Accessors(chain = true)
public class Database {
    private Integer id;
    /**
     * 数据库id
     */
    private String databaseId;
    /**
     * 数据库名（自定义名称）
     */
    private String databaseName;
    /**
     * 数据库连接
     */
    private String databaseConnection;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 数据库类型
     */
    private String databaseType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Database database = (Database) o;
        return Objects.equals(databaseId, database.databaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseId);
    }
}
