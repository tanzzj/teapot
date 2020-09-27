package com.teamer.teapot.datasource.manager.dao;

import com.teamer.teapot.common.model.Database;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Mapper
public interface DatabaseManagementDAO {

    /**
     * query database instances list
     *
     * @param database (databaseName - optional)
     * @return List<Database>
     */
    List<Database> queryDatabaseList(Database database);

    /**
     * add database
     *
     * @param database instance
     * @return int
     */
    int addDatabase(Database database);

    /**
     * 查询database详情
     *
     * @param database (databaseId)
     * @return Database
     */
    Database queryDatabaseDetail(Database database);

}
