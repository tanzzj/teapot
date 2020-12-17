package com.teamer.teapot.datasource.enums;

/**
 * @author tanzj
 * @date 2021/1/13
 */
public enum DatabaseTypeEnum {

    /**
     * mysql
     */
    MYSQL("mysql"),

    /**
     * oracle
     */
    ORACLE("oracle");

    private String databaseName;

    DatabaseTypeEnum(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
