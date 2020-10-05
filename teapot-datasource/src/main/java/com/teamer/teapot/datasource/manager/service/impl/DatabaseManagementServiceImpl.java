package com.teamer.teapot.datasource.manager.service.impl;

import com.github.pagehelper.PageInfo;
import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.datasource.manager.dao.DatabaseManagementDAO;
import com.teamer.teapot.datasource.manager.service.DatabaseManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Slf4j
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


    /**
     * 测试连接
     *
     * @param databaseParams (databaseConnection,databaseUsername,databasePassword)
     * @return Result
     */
    @Override
    public Result testConnection(Database databaseParams) {
        try (
                Connection connection = DriverManager.getConnection(
                        databaseParams.getDatabaseConnection(),
                        databaseParams.getUsername(),
                        databaseParams.getPassword()
                )
        ) {
            return connection.isValid(1000) ?
                    Result.success("connection success") :
                    Result.fail("connection fail");
        } catch (SQLException e) {
            log.error("connection fail", e);
            return Result.fail("connection fail");
        }
    }


}
