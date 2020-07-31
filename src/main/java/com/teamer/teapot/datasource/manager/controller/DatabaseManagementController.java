package com.teamer.teapot.datasource.manager.controller;

import com.teamer.teapot.common.model.Database;
import com.teamer.teapot.common.model.PageParam;
import com.teamer.teapot.common.model.PortalUser;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.datasource.manager.service.DatabaseManagementService;
import com.teamer.teapot.rbac.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@RestController
@RequestMapping("/api/datasource/manager")
public class DatabaseManagementController {

    @Autowired
    DatabaseManagementService databaseManagementService;

    /**
     * query database instances list
     *
     * @param databaseParams (databaseName - optional)
     * @return List<Database>
     */
    @PostMapping(value = "/queryDatabaseList")
    public Result queryDatabaseList(@RequestBody PageParam<Database> databaseParams) {
        return databaseManagementService.queryDatabaseList(databaseParams);
    }


    /**
     * add database
     *
     * @param databaseParams instance
     * @return int
     */
    @PostMapping(value = "/addDatabase")
    public Result addDatabase(@RequestBody Database databaseParams, HttpServletRequest request) {
        ValidationUtil.validateParamsBlankAndNull(
                databaseParams::getDatabaseConnection,
                databaseParams::getDatabaseName,
                databaseParams::getDatabaseType,
                databaseParams::getUsername,
                databaseParams::getPassword
        );
        return databaseManagementService.addDatabase(databaseParams);
    }


}
