package com.teamer.teapot.project.database.dao;

import com.teamer.teapot.common.model.Project;
import com.teamer.teapot.common.model.ProjectDatabase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : tanzj
 * @date : 2020/7/31.
 */
@Mapper
public interface ProjectDBDAO {

    /**
     * add a project related database instance
     *
     * @return int
     */
    int addProjectDatabaseInstance(ProjectDatabase projectDatabase);

    /**
     * query project related database instances list
     *
     * @param project (projectId)
     * @return List<ProjectDatabase>
     */
    List<ProjectDatabase> queryProjectDataBaseList(Project project);
}
