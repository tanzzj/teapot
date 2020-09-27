package com.teamer.teapot.datasource.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.datasource.manager.dao.DatabaseManagementDAO;
import com.teamer.teapot.datasource.manager.service.DatabaseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Service
public class DatabaseManagementServiceImpl implements DatabaseManagementService {


    @Autowired
    DatabaseManagementDAO databaseManagementDAO;

    /**
     * query database instances list
     *
     * @param databaseParams (databaseName - optional)
     * @return List<Database>
     */
    @Override
    public Result queryDatabaseList(PageParam<Database> databaseParams) {
        PageHelperUtil.startPage(databaseParams, true);
        List<Database> databaseList = databaseManagementDAO.queryDatabaseList(databaseParams.getParams());
        return Result.success(new PageInfo<>(databaseList));
    }

    /**
     * add database instance
     *
     * @param database instance
     * @return int
     */
    @Override
    public Result addDatabase(Database database) {
        database.setDatabaseId(UUIDFactory.getShortUUID());
        databaseManagementDAO.addDatabase(database);
        return Result.success(new Database().setDatabaseId(database.getDatabaseId()));
    }
}
