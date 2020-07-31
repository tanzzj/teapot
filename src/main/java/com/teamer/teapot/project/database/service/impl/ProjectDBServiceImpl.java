package com.teamer.teapot.project.database.service.impl;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import com.teamer.teapot.common.model.Result;
import com.teamer.teapot.common.util.PageHelperUtil;
import com.teamer.teapot.common.util.UUIDFactory;
import com.teamer.teapot.project.database.dao.ProjectDBDAO;
import com.teamer.teapot.project.database.service.ProjectDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Service
public class ProjectDBServiceImpl implements ProjectDBService {

    @Autowired
    ProjectDBDAO projectDBDAO;
    /**
     * add a project related database instance
     *
     * @param projectDatabase (
     *                        projectId
     *                        databaseId
     *                        )
     * @return Result
     */
    @Override
    public Result addProjectDatabaseInstance(ProjectDatabase projectDatabase) {
        //todo check id exist
        projectDatabase.setDatabaseId(UUIDFactory.getShortUUID());
        projectDBDAO.addProjectDatabaseInstance(projectDatabase);
        return Result.success(new ProjectDatabase().setDatabaseId(projectDatabase.getDatabaseId()));
    }

    /**
     * query project related database instances list
     *
     * @param project (projectId)
     * @return List<ProjectDatabase>
     */
    @Override
    public Result queryProjectDataBaseList(Project project) {
        List<ProjectDatabase> projectDatabaseList = projectDBDAO.queryProjectDataBaseList(project);
        return Result.success(projectDatabaseList);
    }
}
