package com.teamer.teapot.project.database.service;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.model.SQLParams;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
public interface ProjectDBService {

    /**
     * add a project related database instance
     *
     * @param projectDatabase (
     *                        projectId
     *                        databaseId
     *                        )
     * @return Result
     */
    Result addProjectDatabaseInstance(ProjectDatabase projectDatabase);

    /**
     * query project related database instances list
     *
     * @param project (projectId)
     * @return List<ProjectDatabase>
     */
    Result queryProjectDataBaseList(Project project);

    /**
     * 执行目标db脚本
     * @param sqlParams (
     *                  sql,
     *                  databaseId
     *                  )
     * @return Result
     */
    Result executeSQL(SQLParams sqlParams) throws ClassNotFoundException, SQLException;

}
