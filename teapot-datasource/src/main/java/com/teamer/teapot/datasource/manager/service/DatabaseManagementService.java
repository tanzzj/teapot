package com.teamer.teapot.datasource.manager.service;

import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
public interface DatabaseManagementService {

    /**
     * query database instances list
     * @param databaseParams (databaseName - optional)
     * @return  List<Database>
     */
    Result queryDatabaseList(PageParam<Database> databaseParams);

    /**
     * add database
     * @param databaseParams instance
     * @return int
     */
    Result addDatabase(Database databaseParams);
}
