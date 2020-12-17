package com.teamer.teapot.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认mysql执行器
 *
 * @author tanzj
 */
@Slf4j
public class DefaultDatabaseExecutor extends AbstractDatabaseExecutor {

    @Override
    public Class loadDatabaseDriver(String dbType) {
        try {
            return Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("com.mysql.cj.jdbc.Driver not found");
            return null;
        }
    }
}
