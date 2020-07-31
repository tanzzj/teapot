package com.teamer.teapot.project.database.service;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import com.teamer.teapot.common.model.Result;

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

}
