package com.teamer.teapot.datasource;

import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.datasource.manager.dao.DatabaseManagementDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认mysql执行器
 *
 * @author tanzj
 */
@Slf4j
@Component
public class DefaultDatabaseExecutor extends AbstractDatabaseExecutor {

    @Autowired
    DatabaseManagementDAO databaseManagementDAO;

    @Override
    public Class loadDatabaseDriver(String dbType) {
        try {
            return Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("com.mysql.cj.jdbc.Driver not found");
            return null;
        }
    }

    @Override
    public Database getDatabase(String databaseId) {
        return databaseManagementDAO.queryDatabaseDetail(new Database().setDatabaseId(databaseId));
    }


}
