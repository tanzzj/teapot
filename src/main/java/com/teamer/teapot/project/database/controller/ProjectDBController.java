package com.teamer.teapot.project.database.controller;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.SQLParams;
import com.teamer.teapot.common.util.ValidationUtil;
import com.teamer.teapot.project.database.service.ProjectDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@RestController
@RequestMapping("/api/project/manager/database")
public class ProjectDBController {

    @Autowired
    private ProjectDBService projectDBService;

    /**
     * add a project related database instance
     *
     * @param projectDatabase (
     *                        projectId
     *                        databaseId
     *                        )
     * @return Result
     */
    @PostMapping("/addProjectDatabaseInstance")
    public Result addProjectDatabaseInstance(@RequestBody ProjectDatabase projectDatabase) {
        ValidationUtil.validateParamsBlankAndNull(
                projectDatabase::getDatabaseId,
                projectDatabase::getProjectId
        );
        return projectDBService.addProjectDatabaseInstance(projectDatabase);
    }

    /**
     * query project related database instances list
     *
     * @param project (projectId)
     * @return List<ProjectDatabase>
     */
    @PostMapping("/queryProjectDataBaseList")
    public Result queryProjectDataBaseList(@RequestBody Project project) {
        ValidationUtil.validateParamsBlankAndNull(project::getProjectId);
        return projectDBService.queryProjectDataBaseList(project);
    }

    @PostMapping("/executeSQL")
    public Result executeSQL(@RequestBody SQLParams sqlParams) throws ClassNotFoundException {
        ValidationUtil.validateParamsBlankAndNull(
                sqlParams::getSql,
                sqlParams::getDatabaseId
        );
        return projectDBService.executeSQL(sqlParams);
    }


}
