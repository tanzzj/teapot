package com.teamer.teapot.datasource.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.util.TestUtil;
import com.teamer.teapot.datasource.manager.service.DatabaseManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DatabaseManagementServiceImplTest {

    @Autowired
    DatabaseManagementService databaseManagementService;

    @Test
    public void queryDatabaseList() {
        PageParam<Database> pageParam = new PageParam<>();
        pageParam.setParams(new Database().setDatabaseName("mysql"));
        TestUtil.assertSuccess(
                databaseManagementService.queryDatabaseList(pageParam)
        );
    }

    @Test
    @Transactional
    @Rollback
    public void addDatabase() {
        TestUtil.assertSuccess(
                databaseManagementService.addDatabase(
                        new Database()
                                .setDatabaseConnection("202.135.136.147")
                                .setDatabaseName("mysql2")
                                .setDatabaseType("mysql")
                                .setUsername("tanzj")
                                .setPassword("tanzj")
                )
        );
    }


}