package com.teamer.teapot.datasource;

import com.teamer.teapot.common.model.Database;
import org.springframework.stereotype.Component;

/**
 * @author tanzj
 */
@Component
public class DefaultDatabaseExecutor extends AbstractDatabaseExecutor{
    @Override
    public Class loadDatabaseDriver(String dbType) {
        return null;
    }

    @Override
    public Database getDatabase(String databaseId) {
        return null;
    }
}
